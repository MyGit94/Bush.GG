package com.pinkward.bushgg.domain.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	// 회원 전체 리스트 
	public List<MemberDTO> findByAll();
	
	// 아이디와 비밀번호 찾기
	public MemberDTO findByIdAndPasswd(@Param("loginid") String id,@Param("passwd") String passwd);
	
	// 비밀번호 찾기
	public MemberDTO findPasswd(@Param("loginid") String loginid,
			@Param("nickname") String nickname, @Param("email") String email);
	
	// 닉네임 찾기
	public MemberDTO findByNick(String nickName);
	
	// 닉네임 검색
	public List<MemberDTO> findBysearchNick(String nickName);
	
	// 회원 생성
	public void create(MemberDTO member);
	
	// 닉네임 수정
	public void updateNickName(String nickname);
	
}