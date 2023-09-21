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
	public MemberDTO findByIdAndPasswd(@Param("loginId") String loginId,@Param("passwd") String passwd);
	
	// 비밀번호 찾기
	public MemberDTO findByPasswd(@Param("loginId") String loginid,
			@Param("nickName") String nickname, @Param("email") String email);
	
	// 검색 타입별 회원 검색
	public List<MemberDTO> findBySearchNick(@Param("type") String type, @Param("value") String value);
	
	
	// 닉네임 찾기
	public MemberDTO findByNick(String nickName);
	

	// 회원 생성
	public void create(MemberDTO memberDTO);
	
	// 닉네임 수정
	public void updateNickName(MemberDTO memberDTO);
	
	// 중복체크
	
    // 로그인 아이디 회원 존재 여부 확인
	boolean checkLoginId(String loginId);
	
	// 닉네임으로 회원 존재 여부 확인
	boolean checkNickName(String nickName);
	
	// 이메일으로 회원 존재 여부 확인
	boolean checkEmail(String email);
	
}