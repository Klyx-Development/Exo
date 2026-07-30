package org.klyx.exo.util;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implements a simple multimap that is thread-safe.
 */
public class ThreadsafeMultimap<K, V> implements Multimap<K, V> {
    private final Map<K, Collection<V>> backing;

    public ThreadsafeMultimap() {
        this.backing = new ConcurrentHashMap<>();
    }

    public ThreadsafeMultimap(Map<K, Collection<V>> backing) {
        this.backing = backing;
    }

    @Override
    public boolean put(K key, V value) {
        return backing.computeIfAbsent(key, k -> new Values(k)).add(value);
    }

    @Override
    public int size() {
        return backing.values().stream().mapToInt(Collection::size).sum();
    }

    @Override
    public boolean isEmpty() {
        return backing.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backing.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backing.values().stream().anyMatch(collection -> collection.contains(value));
    }

    @Override
    public boolean containsEntry(Object key, Object value) {
        Collection<V> values = backing.get(key);
        return values != null && values.contains(value);
    }

    @Override
    public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
        Values newList = new Values(key);
        values.forEach(newList::add);
        backing.put(key, newList);
        return newList;
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean changed = false;
        for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
            if (put(entry.getKey(), entry.getValue())) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean putAll(K key, Iterable<? extends V> values) {
        boolean changed = false;
        for (V value : values) {
            if (put(key, value)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean remove(Object key, Object value) {
        Collection<V> values = backing.get(key);
        if (values == null) {
            return false;
        }

        boolean result = values.remove(value);
        if (result && values.isEmpty()) {
            backing.remove(key);
        }
        return result;
    }

    @Override
    public Collection<V> removeAll(Object key) {
        Collection<V> removed = backing.remove(key);
        return removed != null ? new ArrayList<>(removed) : new ArrayList<>();
    }

    @Override
    public void clear() {
        backing.clear();
    }

    @Override
    public Collection<V> get(K key) {
        return backing.getOrDefault(key, new Values(key));
    }

    @Override
    public Set<K> keySet() {
        return backing.keySet();
    }

    @Override
    public Multiset<K> keys() {
        Multiset<K> result = HashMultiset.create();
        for (Map.Entry<K, Collection<V>> entry : backing.entrySet()) {
            result.add(entry.getKey(), entry.getValue().size());
        }
        return result;
    }

    @Override
    public Collection<V> values() {
        return new MultimapValueCollection();
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        throw new UnsupportedOperationException("Iterating over entries of threadsafe multimap is not supported");
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return backing;
    }

    /**
     * The guiType used for the internal values.
     */
    private class Values extends AbstractCollection<V> {
        private final K key;
        private final List<V> inner;

        public Values(K key) {
            this.key = key;
            this.inner = new CopyOnWriteArrayList<>();
        }

        @Override
        public int size() {
            return inner.size();
        }

        @Override
        public boolean add(V element) {
            if (isEmpty()) {
                // If this value is currently empty it is not registered on the parent!
                if (!backing.containsKey(key)) {
                    throw new IllegalStateException("Fetching two mutable values and editing both is not allowed in the ThreadsafeMultimap");
                }
                backing.put(key, this);
            }
            return inner.add(element);
        }

        @Override
        public void clear() {
            inner.clear();
        }

        @Override
        public boolean isEmpty() {
            return inner.isEmpty();
        }

        @Override
        public Iterator<V> iterator() {
            return new InnerIterator();
        }

        @Override
        public boolean retainAll(Collection<?> elements) {
            boolean changed = false;
            Iterator<V> it = iterator();
            while (it.hasNext()) {
                V element = it.next();
                if (!elements.contains(element)) {
                    it.remove();
                    changed = true;
                }
            }
            return changed;
        }

        @Override
        public boolean removeAll(Collection<?> elements) {
            boolean changed = false;
            for (Object element : elements) {
                if (remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }

        @Override
        public boolean remove(Object element) {
            boolean result = inner.remove(element);
            // If we removed an element and the list is now empty we remove it
            if (result && isEmpty()) {
                backing.remove(key);
            }
            return result;
        }

        @Override
        public boolean containsAll(Collection<?> elements) {
            return inner.containsAll(elements);
        }

        @Override
        public boolean contains(Object element) {
            return inner.contains(element);
        }

        @Override
        public boolean addAll(Collection<? extends V> elements) {
            boolean changed = false;
            for (V element : elements) {
                if (add(element)) {
                    changed = true;
                }
            }
            return changed;
        }

        /**
         * An iterator for editing the values sublist.
         */
        private class InnerIterator implements Iterator<V> {
            private final Iterator<V> iterator = inner.iterator();
            private V last = null;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public V next() {
                V next = iterator.next();
                last = next;
                return next;
            }

            @Override
            public void remove() {
                if (last != null) {
                    inner.remove(last);
                    if (inner.isEmpty()) {
                        backing.remove(key);
                    }
                }
            }
        }
    }

    /**
     * Returns the values of a multimap.
     */
    private class MultimapValueCollection extends AbstractCollection<V> {
        @Override
        public int size() {
            return backing.values().stream().mapToInt(Collection::size).sum();
        }

        @Override
        public void clear() {
            backing.clear();
        }

        @Override
        public boolean isEmpty() {
            return backing.values().stream().allMatch(Collection::isEmpty);
        }

        @Override
        public Iterator<V> iterator() {
            return new InnerIterator();
        }

        @Override
        public boolean retainAll(Collection<?> elements) {
            boolean changed = false;
            Iterator<V> it = iterator();
            while (it.hasNext()) {
                V element = it.next();
                if (!elements.contains(element)) {
                    it.remove();
                    changed = true;
                }
            }
            return changed;
        }

        @Override
        public boolean removeAll(Collection<?> elements) {
            boolean changed = false;
            for (Object element : elements) {
                if (remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }

        @Override
        public boolean remove(Object element) {
            boolean changed = false;
            Set<K> keysToRemove = new HashSet<>();

            for (Map.Entry<K, Collection<V>> entry : backing.entrySet()) {
                Collection<V> values = entry.getValue();
                if (values.remove(element)) {
                    changed = true;
                    if (values.isEmpty()) {
                        keysToRemove.add(entry.getKey());
                    }
                }
            }

            keysToRemove.forEach(backing::remove);
            return changed;
        }

        @Override
        public boolean containsAll(Collection<?> elements) {
            return elements.stream().allMatch(this::contains);
        }

        @Override
        public boolean addAll(Collection<? extends V> elements) {
            throw new UnsupportedOperationException("Cannot add to a multimap values() object");
        }

        @Override
        public boolean add(V element) {
            throw new UnsupportedOperationException("Cannot add to a multimap values() object");
        }

        @Override
        public boolean contains(Object element) {
            return backing.values().stream().anyMatch(values -> values.contains(element));
        }

        /**
         * An iterator for editing the values sublist.
         */
        private class InnerIterator implements Iterator<V> {
            private final Iterator<Map.Entry<K, Collection<V>>> iterator = backing.entrySet().iterator();
            private Iterator<V> currentSubIterator = null;
            private K lastKey = null;
            private V lastValue = null;

            @Override
            public boolean hasNext() {
                // If the sub-iterator has a result we can go!
                if (currentSubIterator != null && currentSubIterator.hasNext()) {
                    return true;
                }

                // If the sub-iterator is done we want to go to the
                // next entry on the main iterator, or stop if we have nothing
                while (iterator.hasNext()) {
                    Map.Entry<K, Collection<V>> entry = iterator.next();
                    lastKey = entry.getKey();
                    currentSubIterator = entry.getValue().iterator();
                    if (currentSubIterator.hasNext()) {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public V next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                lastValue = currentSubIterator.next();
                return lastValue;
            }

            @Override
            public void remove() {
                if (lastKey != null && lastValue != null) {
                    ThreadsafeMultimap.this.remove(lastKey, lastValue);
                }
            }
        }
    }
}