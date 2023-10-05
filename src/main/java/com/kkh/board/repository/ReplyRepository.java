package com.kkh.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kkh.board.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	@Modifying
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(Long userId, Long boardId, String content); // 업데이트된 행의 개수를 리턴해줌.
}
