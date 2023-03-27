package com.baechu.book.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.baechu.book.dto.BookListDto;
import com.baechu.book.dto.FilterDto;
import com.baechu.book.entity.Book;
import com.baechu.book.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	@GetMapping("/detail/{id}")
	public String detailPage(Model model, @PathVariable Long id) {

		Map<String, Object> info = bookService.bookdetail(id);

		model.addAttribute("info", info);

		return "detail";
	}

	@GetMapping("/search")
	public String searchByWord(
		Model model,
		@RequestParam(value = "query", defaultValue = "") String query,
		@RequestParam(value = "sort", defaultValue = "") Integer sort,
		@RequestParam(value = "year", defaultValue = "0") Integer year,
		@RequestParam(value = "star", defaultValue = "") Integer star,
		@RequestParam(value = "minPrice", defaultValue = "") Integer minPrice,
		@RequestParam(value = "maxPrice", defaultValue = "") Integer maxPrice,
		@RequestParam(value = "publish", defaultValue = "") String publish,
		@RequestParam(value = "author", defaultValue = "") String author,
		@RequestParam(value = "totalRow", defaultValue = "10") Integer totalRow,
		@RequestParam(value = "category", defaultValue = "") String category,
		@RequestParam(value = "babyCategory", defaultValue = "") String babyCategory,
		@RequestParam(value = "cursor", defaultValue = "1") Long cursor
	) {
		FilterDto filter = createDto(query, sort, year, star, minPrice, maxPrice, publish, author, totalRow,
			category, babyCategory, cursor);
		BookListDto result = bookService.searchByCursor(filter);
		model.addAttribute("result", result);
		return "search";
	}

	@GetMapping("/main")
	public String bookList(Model model) {

		List<Book> list = bookService.bookList();
		model.addAttribute("list", list);

		return "main";
	}

	private FilterDto createDto(String query, Integer sort, Integer year, Integer star, Integer minPrice,
		Integer maxPrice, String publish, String author, Integer totalRow, String category,
		String babyCategory, Long cursor) {
		// 나중에 RequestParam을 따로 받지말고 하나의 객체로 받도록 수정 필요.
		return FilterDto.builder()
			.query(query)
			.sort(sort)
			.year(year)
			.star(star)
			.minPrice(minPrice)
			.maxPrice(maxPrice)
			.publish(publish)
			.author(author)
			.totalRow(totalRow)
			.category(category)
			.babyCategory(babyCategory)
			.cursor(cursor)
			.build();
	}
}