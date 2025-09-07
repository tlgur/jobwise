# TODO (branch: domain/create_entity)

> 이 문서는 브랜치별로 작업 진행 상황과 다음 할 일을 정리하기 위한 개인/팀 메모입니다.  
> 머지할 때 충돌이 나면 필요 없는 항목은 지우고, 공통 TODO는 main/dev 쪽에 합쳐주세요.

---

## 📝 목표
- [ ] .java 엔티티 파일 작성
- [ ] ddl sql 문 작성

---

## ✅ 현재까지 완료한 것
- [x] 엔티티 기본 구조 설계 (`Content`, `Post`, `Reply`, `User`, `JobCategory`, 매핑 테이블)
- [x] 엔티티 매핑 관계 설정 완료 

---

## 📝 다음에 할 것
- [ ] 엔티티 상 제약조건 명시
- [ ] 엔티티 생성용 정적 팩토리 메서드 & private 생성자
- [ ] 연관관계 편의 메서드
- [ ] DDD 메서드
- [ ] 테스트 작성

---

## 💡 메모 / 아이디어
- 댓글/대댓글 depth 계산을 DB 인덱스로 최적화할 필요가 있음
- Flyway DDL에서 `component(parent_id, depth)` 인덱스 고려
