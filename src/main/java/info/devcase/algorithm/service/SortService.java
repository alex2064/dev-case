package info.devcase.algorithm.service;

import java.util.HashMap;

import org.springframework.ui.ModelMap;

public interface SortService {
	
	public void swap(int[] arr, int i, int j) throws Exception;
	
	public ModelMap bubbleSort(HashMap<String, Object> param) throws Exception;
	
	public ModelMap selectionSort(HashMap<String, Object> param) throws Exception;
	
	public ModelMap insertionSort(HashMap<String, Object> param) throws Exception;
	
	public ModelMap quickSort(HashMap<String, Object> param) throws Exception;
	
	public ModelMap mergeSort(HashMap<String, Object> param) throws Exception;
}
