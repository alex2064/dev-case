package info.devcase.main;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.ui.ModelMap;

import info.devcase.algorithm.service.SortService;
import info.devcase.algorithm.service.impl.SortServiceImpl;

public class SortTest {
	
	@Test
	public void test() throws Exception {
		int[] arr = {6,9,4,2,8,7,5,1,3,0};
		HashMap<String, Object> param = new HashMap<>();
		param.put("array", arr);
		
		SortService sortService = new SortServiceImpl(); 
		
		//ModelMap model = sortService.bubbleSort(param);
		//ModelMap model = sortService.selectionSort(param);
		//ModelMap model = sortService.insertionSort(param);
		//ModelMap model = sortService.quickSort(param);
		//ModelMap model = sortService.mergeSort(param);
		ModelMap model = sortService.heapSort(param);
		
		int[] result = (int[])model.getAttribute("array");	// arr과 동일한 배열
		
		System.out.println(Arrays.toString(result));
	}
}
