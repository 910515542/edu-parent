package com.xiaoxuan.ucenter.mapper;

import com.xiaoxuan.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author xiaoxuan
 * @since 2021-04-06
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    Integer selectRegisterCount(@Param("day") String day);
}
