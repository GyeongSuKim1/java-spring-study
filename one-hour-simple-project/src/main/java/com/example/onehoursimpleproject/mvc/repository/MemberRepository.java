package com.example.onehoursimpleproject.mvc.repository;

import com.example.onehoursimpleproject.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
