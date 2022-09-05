package info.devcase.algorithm.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import info.devcase.algorithm.service.SortService;

@Service("SortService")
public class SortServiceImpl implements SortService{
	
	@Override
	public void swap(int[] arr, int i, int j) throws Exception{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	
	@Override
	public ModelMap bubbleSort(HashMap<String, Object> param) throws Exception{
		ModelMap model = new ModelMap();
		
		int[] arr = (int[])param.get("array");
		
		for(int i=arr.length-1; i>=1; i--) {
			for(int j=0; j<i; j++) {
				if(arr[j] > arr[j+1]) {
					swap(arr, j, j+1);
				}
			}
		}
		
		model.addAttribute("array", arr);
		return model;
	}
	
	
	@Override
	public ModelMap selectionSort(HashMap<String, Object> param) throws Exception{
		ModelMap model = new ModelMap();
		
		int[] arr = (int[])param.get("array");
		int minIndex;
		for(int i=0; i<arr.length-1; i++) {
			minIndex = i;
			for(int j=i+1; j<arr.length; j++) {
				if(arr[minIndex] > arr[j]) {
					minIndex = j;
				}
			}
			
			swap(arr, i, minIndex);
		}
		
		model.addAttribute("array", arr);
		return model;
	}
	
	
	@Override
	public ModelMap insertionSort(HashMap<String, Object> param) throws Exception{
		ModelMap model = new ModelMap();
		
		int[] arr = (int[])param.get("array");
		int value;
		for(int i=1; i<arr.length; i++) {
			value = arr[i];
			for(int j=i-1; j>=0; j--) {
				if(value < arr[j]) {
					arr[j+1] = arr[j];
				}else {
					arr[j+1] = value;
					break;
				}
				
				if(j == 0) {
					arr[j+1] = arr[j];
					arr[j] = value;
				}
			}
		}
		
		model.addAttribute("array", arr);
		return model;
	}
}
