package info.devcase.algorithm.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
					arr[j] = value;
				}
			}
		}
		
		model.addAttribute("array", arr);
		return model;
	}
	
	
	@Override
	public ModelMap quickSort(HashMap<String, Object> param) throws Exception{
		ModelMap model = new ModelMap();
		
		int[] arr = (int[])param.get("array");
		
		quick(arr, 0, arr.length-1);
		
		model.addAttribute("array", arr);
		return model;
	}
	
	public void quick(int[] arr, int left, int right) throws Exception{
		if(left >= right) return;
		
		int pivot = partition(arr, left, right);
		
		// pivot index 기준으로 좌우 다시 퀵정렬
		quick(arr, left, pivot-1);
		quick(arr, pivot+1, right);
		
	}
	
	public int partition(int[] arr, int left, int right) throws Exception{
		int pivot = arr[left];
		int i = left;
		
		while(left < right) {
			while(pivot < arr[right]) {
				right--;
			}
			while(pivot >= arr[left] && left < right) {
				left++;
			}
			
			swap(arr, left, right);
		}
		
		arr[i] = arr[left];
		arr[left] = pivot;
		
		return left;
	}
	
	@Override
	public ModelMap mergeSort(HashMap<String, Object> param) throws Exception{
		ModelMap model = new ModelMap();
		
		int[] arr = (int[])param.get("array");
		
		mergeCut(arr, 0, arr.length-1);
		
		model.addAttribute("array", arr);
		return model;
	}
	
	public void mergeCut(int[] arr, int left, int right) throws Exception{
		if(left == right) return;
		
		int mid = (left+right)/2;
		
		// 제일 작은단위로 자른 후 merge 하면서 정렬
		mergeCut(arr, left, mid);
		mergeCut(arr, mid+1, right);
		merge(arr, left, mid, right);
		
	}
	
	public void merge(int[] arr, int left, int mid, int right) throws Exception{
		int[] arrLeft = Arrays.copyOfRange(arr, left, mid+1);
		int[] arrRight = Arrays.copyOfRange(arr, mid+1, right+1);
		int i = 0, j = 0, k = left;
		
		while(i<arrLeft.length && j<arrRight.length) {
			if(arrLeft[i] <= arrRight[j]) {
				arr[k++] = arrLeft[i++]; 
			}else {
				arr[k++] = arrRight[j++];
			}
		}
		
		while(i<arrLeft.length) {
			arr[k++] = arrLeft[i++];
		}
		while(j<arrRight.length) {
			arr[k++] = arrRight[j++];
		}
	}
	
	
	@Override
	public ModelMap heapSort(HashMap<String, Object> param) throws Exception{
		ModelMap model = new ModelMap();
		
		int[] arr = (int[])param.get("array");
		
		for(int i=arr.length/2-1; i>= 0; i--) {
			heapify(arr, i, arr.length-1);
		}
		
		for(int i=1; i<arr.length; i++) {
			int last = arr.length-i;
			swap(arr, 0, last);
			heapify(arr, 0, last-1);
		}
		
		model.addAttribute("array", arr);
		return model;
	}
	
	public void heapify(int[] arr, int i, int last) throws Exception{
		int parent = i;
		int left = i*2+1;
		int right = i*2+2;
		
		if(left <= last && arr[parent] < arr[left]) {
			parent = left;
		}
		if(right <= last && arr[parent] < arr[right]) {
			parent = right;
		}
		
		if(parent != i) {
			swap(arr, i, parent);
			heapify(arr, parent, last);
		}
	}

}
