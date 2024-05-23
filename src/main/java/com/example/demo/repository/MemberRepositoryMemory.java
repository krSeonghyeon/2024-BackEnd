package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Member;

@Repository
public class MemberRepositoryMemory implements MemberRepository {

    private static final Map<Long, Member> members = new HashMap<>();
    private static final AtomicLong autoincrement = new AtomicLong(1);

    static {
        // 1번 유저를 미리 만들어둔다.
        members.put(autoincrement.getAndIncrement(), new Member("최준호", "temp@gmail.com", "password"));
    }

    @Override
    public List<Member> findAll() {
        return members.entrySet().stream()
            .map(it -> {
                Member member = it.getValue();
                member.setId(it.getKey());
                return member;
            }).toList();
    }

    @Override
    public Member findById(Long id) {
        return members.getOrDefault(id, null);
    }

    @Override
    public void insert(Member member) {
        members.put(autoincrement.getAndIncrement(), member);
    }
}
