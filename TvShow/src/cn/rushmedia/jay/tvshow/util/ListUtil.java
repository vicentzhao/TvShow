package cn.rushmedia.jay.tvshow.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ListUtil {
	

	
	 public ArrayList getlist(ArrayList list,ArrayList list2){ 
	  List <Integer> l1=new ArrayList<Integer>();
	  l1.add(1);l1.add(2);l1.add(3);  l1.add(4);l1.add(5);l1.add(6);  
	List <Integer> l2=new ArrayList<Integer>(); 
	 l2.add(4);l2.add(5);l2.add(6);  l2.add(7);l2.add(8);l2.add(9); 
	 Set<Integer> s=new TreeSet(l1);
	  for(Integer i:l2){   //����Ӳ��ɹ���ʱ�� ˵��s���Ѿ����ڸö���ֱ��remove���ö��󼴿�   
	if(!s.add(i))
	 s.remove(i); 
	 } 
	 System.out.println(s);
	//������������һ�ַ���  
	List <Integer> temp=new ArrayList<Integer>(l1);
	//�����������߹�ͬ�е����� 
	temp.retainAll(l2);  
	l1.removeAll(temp);
	//l1��ȥ�����߹�ͬ�е����� 
	 l2.removeAll(temp);
	//l2��ȥ�����߹�ͬ�е�����   
	 List <Integer> l3=new ArrayList<Integer>();
	  l3.addAll(l1); 
	 l3.addAll(l2); 
	 System.out.println(l3);
	 return null;
	 
	 } 
}
