/*
 * Copyright (C) 2014 Bernardo Sulzbach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.dungeon.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CircularList class that provides a list with maximum capacity.
 * <p/>
 * After the specified maximum capacity is reached, when a new element is added, the oldest element on the list
 * is removed.
 * <p/>
 * Created by Bernardo Sulzbach on 10/12/14.
 */
public final class CircularList<T> implements Serializable {

    public final int capacity;
    private final ArrayList<T> list;
    /**
     * The index where the element that should be at 0 actually is. Initially 0.
     */
    private int zeroIndex;

    public CircularList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive.");
        }
        list = new ArrayList<T>(capacity);
        this.capacity = capacity;
    }

    /**
     * Add an element to this CircularList.
     *
     * @param t the element to be added.
     */
    public void add(T t) {
        if (isFull()) {
            list.set(zeroIndex, t);
            incrementZeroIndex();
        } else {
            list.add(t);
        }
    }

    /**
     * Increments the zeroIndex variable, setting it to 0 if it is equal to the capacity.
     */
    private void incrementZeroIndex() {
        zeroIndex = (zeroIndex + 1) % capacity;
    }

    /**
     * Returns the number of elements in this CircularList.
     *
     * @return the number of elements in this CircularList.
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns true if this CircularList is at its maximum capacity. False otherwise.
     *
     * @return true if this CircularList is at its maximum capacity. False otherwise.
     */
    public boolean isFull() {
        return size() == capacity;
    }

    /**
     * Returns true if this CircularList contains no elements.
     *
     * @return true if this CircularList contains no elements.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T get(final int index) {
        return list.get((index + zeroIndex) % capacity);
    }

}
