create database article;
use article;

select count(*) from article;
select * from article.article;

############################# 게시글 목록조회  ##############################
#########################################################################
select * from article a
where a.board_id = ?
order by a.created_at desc
    limit ?
offset ?;

# Query Plan 조회
##########################################################################################
-- 결과                                                                                   #
-- type = ALL                                                                            #
-- 테이블 전체를 읽는다.(풀 스캔)                                                               #
-- Extras = Using where; Using filesort                                                  #
-- where절로 조건에 대해 필터링.                                                               #
-- 데이터가 많기 때문에 메모리에서 정렬을 수행할 수 없어서, 파일(디스크)에서 데이터를 정렬하는 filesort 수행.   #
##########################################################################################
explain select * from article a
        where a.board_id = 1
        order by a.created_at desc
              limit 30
        offset 90;

# 조회 성능 향상을 위해 index 쿼리 추가
create index idx_board_id_article_id on article(board_id asc,
                                                article_id desc);

explain select * from article a
        where a.board_id = 1
        order by a.article_id desc
            limit 30
        offset 1499970;

# INDEX 만 사용하여 조회하면 빠르게 조회 가능하다.
explain select a.board_id, a.article_id from article a
        where a.board_id = 1
        order by article_id desc
              limit 30 offset 1499970;

# 추출된 30건의 article_id에 대해서만 Clustered Index 에 접근하여
# 30건의 article_id를 sub query 의 결과로 만들고, article 테이블과 join.
    # 이렇게 진행 하더라도 근본적으로 offset 이 커진다면 그 크기만큼 Index Scan 이 필요하여 데이터에 접근하지 않더라도 성능이 저하된다.
explain select * from (
                            select a.article_id from article a
                            where a.board_id = 1
                            order by a.article_id desc
                                limit 30 offset 8999970
                        ) t left join article on t.article_id = article.article_id;
# 해당 문제를 해결하기 위해 몇가지 방법이 있음.
# 방법1. 데이터를 한번 더 분리한다.
    # 예를 들어 게시글을 1년 단위로 테이블 분리
        -- 개별 테이블의 크기를 작게 만든다.
        -- 각 단위에 대해 전체 게시글 수를 관리한다.
    # offset 을 인덱스 페이지 단위 skip 하는 것이 아니라, 1년 동안 작성된 게시글 수 단위로 즉시 skip 한다.
        -- 조회 하고자 하는 offset 이 1년 동안 작성된 게시글 수 보다 크다면, 해당 개수 만큼 즉시 skip or 더 큰 단위로 skip 을 수행하게 되는 것
        -- 애플리케이션에서 이처럼 처리하기 위한 코드 작성 필요

# 방법2. 상식적으로 생각하여 300,000번 페이지를 조회하는게 정상적인 사용자는 아닐 것 이다.
        -- 데이터 수집을 목적으로 하는 어뷰저일 수도 있다.
        -- 정책으로 풀어내자 ex) 게시글 목록 조회는 10,000번 페이지까지 제한한다.
        -- 시간 범위 또는 텍스트 검색 기능을 제공할 수도 있다.

# 방법3. 무한 스크롤
        -- 페이지 번호 방식에서는 동작 특성 상, 뒷 페이지로 갈수록 속도가 느려질 수 밖에 없다.
        -- 하지만 무한 스크롤에서는 아무리 뒷 페이지로 가더라도 균등한 조회 속도를 가진다.

/**
# 마무리
- 페이지 번호 방식에서는 다음 두가지 정보가 필요하다고 했다.
      - N번 페이지에서 M개의 게시글 (해결)
      - 게시글의 개수

- N번 페이지에서 M개의 게시글은 해결 완료
- 다음으로 게시글의 개수를 해결하자
*/
#########################################################################
############################# 게시글 목록조회 끝  ############################

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

############################# 무한 스크롤 ##################################
#########################################################################
/**
  무한 스크롤은 마지막으로 불러온 데이터를 기준점으로 활용
 */

-- 1페이지 (마지막 으로 끝나는 article_id의 값은 224815297942286745L)
select * from article
where board_id = 1
order by article_id desc
    limit 30;
-- 2페이지
select * from article
where board_id = 1
  and article_id < 224815297942286745
order by article_id desc
    limit 30;

-- 마지막 페이지 시작점 찾기 (224812880026693652L)
select * from article a
where a.board_id = 1
order by article_id asc
    limit 1 offset 30;
-- 마지막 페이지
select * from article a
where a.board_id = 1
  and a.article_id < 224812880026693652
order by a.article_id desc
    limit 30;
#########################################################################
############################# 무한 스크롤 끝 ###############################


