package com.pinkward.bushgg.domain.member.mapper;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 회원 관련 SQL Mapper
 */
@Mapper
public interface MemberMapper {

	/** 아이디와 비밀번호 찾기 */
	public MemberDTO findByIdAndPasswd(@Param("loginId") String loginId,@Param("passwd") String passwd);

	/** 회원 생성 */
	public void create(MemberDTO memberDTO);

	/** 로그인 아이디로 회원 존재 여부 확인 */
	boolean checkLoginId(String loginId);

	/** 닉네임으로 회원 존재 여부 확인 */
	boolean checkNickName(String nickName);

	/** 이메일으로 회원 존재 여부 확인 */
	boolean checkEmail(String email);

	/** 로그인 아이디로 Member 객체 반환 */
	@Select("SELECT * FROM MEMBER WHERE loginId = #{loginId}")
	MemberDTO getUserById(String loginId);

}