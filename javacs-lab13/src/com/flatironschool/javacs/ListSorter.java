/**
 *
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * Returns a list that might be new.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		int size = list.size();
		if (size <= 1) {
			return list;
		}

		List<T> first = mergeSort(new LinkedList<T>(list.subList(0, size/2)), comparator);
		List<T> second = mergeSort(new LinkedList<T>(list.subList(size/2, size)), comparator);

		return merge(first, second, comparator);
	}

	/**
	 * Merges two sorted lists into a single sorted list.
	 *
	 * @param first
	 * @param second
	 * @param comparator
	 * @return
	 */
	private List<T> merge(List<T> first, List<T> second, Comparator<T> comparator) {
		List<T> result = new LinkedList<T>();
		int total = first.size() + second.size();
		for (int i=0; i<total; i++) {
			List<T> smallest = smallest(first, second, comparator);
			result.add(smallest.remove(0));
		}
		return result;
	}

	private List<T> smallest(List<T> first, List<T> second, Comparator<T> comparator) {
		if (first.size() == 0) {
			return second;
		}
		if (second.size() == 0) {
			return first;
		}
		if (comparator.compare(first.get(0), second.get(0)) <= 0) {
			return first;
		}
		return second;
	}


	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> queue = new PriorityQueue<T>(list.size(), comparator);
		for (T element : list) {
			queue.offer(element);
		}
		list.clear();
		while (!queue.isEmpty()) {
			list.add((T) queue.poll());
		}
	}


	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 *
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        List<T> topK = new ArrayList<T>();
		PriorityQueue<T> queue = new PriorityQueue<T>(list.size(), comparator);
		for (T element : list) {
			if (queue.size() < k) {
				queue.offer(element);
			} else {
				if (comparator.compare(element, queue.peek()) > 0) {
					queue.poll();
					queue.offer(element);
				}
			}
		}
		while (!queue.isEmpty()) {
			topK.add(queue.poll());
		}
        return topK;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
