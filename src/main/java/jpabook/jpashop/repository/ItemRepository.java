package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        //jpa 저장전 id값이 없기 때문에 없다는 것은 완전 새로운 값, 신규로 등록하는 것
        // 값이 있다면 디비에 있는것
        if (item.getId() == null){
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        //여러건 찾는것은 JPQL
        return em.createQuery("select i from Item  i", Item.class)
                .getResultList();
    }

}
