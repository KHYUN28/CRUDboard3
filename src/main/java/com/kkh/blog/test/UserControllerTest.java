package com.kkh.blog.test;

import java.util.List;

import javax.transaction.Transactional;

import com.kkh.blog.config.RoleType;
import com.kkh.blog.model.User;
import com.kkh.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserControllerTest {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{id}")
	public User Userdetail(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("해당 사용자가 없습니다.");
		});
		return user;
	}

	@GetMapping
	public List<User> Userlist() {
		return userRepository.findAll();
	}

	@GetMapping("/user")
	public Page<User> UserpageList(@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);

		List<User> users = pagingUser.getContent();
		return pagingUser;
	}


	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다. id : "+id;
	}
	
	@Transactional
	@PutMapping("/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터를 요청 => Java Object(MessageConverter의 Jackson라이브러리)가 변환해서 받아줘요.
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		return user;
	}

	@PostMapping("/join")
	public String join(User user) { // key=value (약속된 규칙)
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getId());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
