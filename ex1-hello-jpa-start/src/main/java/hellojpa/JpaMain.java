package hellojpa;

import jakarta.persistence.*;

import java.util.logging.Logger;

public class JpaMain {

    private static final Logger log = Logger.getLogger(JpaMain.class.getName());

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Member 객체 생성 *비영속 상태
            final Member memberToSave = new Member();
            memberToSave.setId(1L);
            memberToSave.setName("Kim");

            // 저장 *영속 상태
            em.persist(memberToSave);

            // 조회
            Member member = em.find(Member.class, memberToSave.getId());
            // 업데이트
            member.setName("Jack");

            // 삭제
            em.remove(member);

            tx.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
