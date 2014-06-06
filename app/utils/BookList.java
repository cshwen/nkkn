package utils;

import java.util.ArrayList;
import java.util.List;

import models.Book;

public class BookList {
	public int page_now;
	public int page_num;
	public List<Book> books;

	public void getPageNum(int sum) {
		if (sum % 21 == 0)
			sum /= 21;
		else
			sum = sum / 21 + 1;
		this.page_num = sum;
	}

	public List<Integer> getPages(int page_now) { // 页码list
		int start = page_now - 2;
		int end = page_now + 2;
		if (start < 1)
			start = 1;
		if (end > page_num)
			end = page_num;
		List<Integer> list = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			list.add(new Integer(i));
		}
		return list;
	}
}
