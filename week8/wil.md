## 서비스 계층
- 어플리케이션의 비즈니스 로직 (동작 구현)
- 레포지토리와 엔티티 또는 DTO로 소통
### 비즈니스 로직
- 구체적인 **기능**
    - 에러 처리
    - 유효성 검사
    - 정책 준수 검사
### 서비스 계층의 메소드
- 비즈니스 로직의 구현
- 메소드는 atomic하게 실행됨
#### 코드: `TodoService`
```java
@Service
@RequiredArgsConstructor
public class TodoService {
    // 필요한 레포지토리 클래스 멤버 주입
    private final TodoRepository todoRepository;

    // 메소드 정의: Transactional 어노테이션 사용
    @Transactional
    public void createTodo() {
        // ...
    }

    // 조회용 메소드에는 Transactional 어노테이션에 readOnlt=true 설정
    // 예상치 못한 수정 방지
    @Transactional(readOnly = true)
    public List<Todo> getTodoList(Long memberId, Long requestedMemberId) throws Exception{
        // ...
        return todoRepository.findAllByMember(member);
    }

    // 예외를 던지는 메소드: throws Exceptions 붙여야 함
    @Transactional
    public void deleteTodo(Long memberId, Long todoId) throws Exception {
        // ...
        if (todo.getMember() != member) {
            throw new Exception("Access denied.");
        }
        // ...
    }
}
```
### 서비스 테스트
#### 단위 테스트
- 단독으로 테스트 (다른 클래스에 의존하지 않음)
    - 이를 위해 mock이라는 가짜 객체 사용
    - Mockito 라이브러리 사용
#### 코드: `TodoServiceTest`
```java
@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    // Mock 어노테이션을 사용하여 주입될 객체를 모킹
    @Mock
    private TodoRepository todoRepository;

    // Mock 주입
    @InjectMocks
    TodoService todoService;
    
    // ...

    @Test
    void testTodoService() throws Exception {
        // given(): mock 객체의 특정 메소드 호출 시 반환값 임의 지정 가능
        // given(<class>.<method>(<actual-parameter>)).willReturn(<something>)
        given(memberRepository.findById(anyLong())).willReturn(new Member());

        // when
        todoService.createTodo("content", 1L);

        // verify(): mock 객체의 특정 메서드를 몇번 호출 했는지 검증
        // mock객체의 save가 1번 호출됐는지 검증
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    // 에러가 잘 발생하는지 테스트
    @Test
    void testTodoServiceFail() throws Exception {
        // ...
        Assertions.assertThatThrownBy(() -> todoService.createTodo("content", 1L))
            .isInstanceOf(Exception.class)
            .hasMessage(/*에러메시지*/);
    }
}
```

