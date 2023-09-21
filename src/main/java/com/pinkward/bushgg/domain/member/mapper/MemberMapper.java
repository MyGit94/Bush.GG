package com.pinkward.bushgg.domain.member.mapper;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * Member 관련 SQL Mapper
 */
@Mapper
public interface MemberMapper {

	public List<MemberDTO> findByAll();

	public MemberDTO findByIdAndPasswd(@Param("loginId") String loginId,@Param("passwd") String passwd);

	public MemberDTO findByPasswd(@Param("loginId") String loginid,
			@Param("nickName") String nickname, @Param("email") String email);

	public List<MemberDTO> findBySearchNick(@Param("type") String type, @Param("value") String value);

	public MemberDTO findByNick(String nickName);

	public void create(MemberDTO memberDTO);

	public void updateNickName(MemberDTO memberDTO);

	@Select("SELECT * FROM MEMBER WHERE loginId = #{loginId}")
	MemberDTO getUserById(String loginId);
}