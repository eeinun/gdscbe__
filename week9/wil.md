## 컨트롤러
- 클라이언트와 서버의 인터페이스 역할
- http 프로토콜 사용
- 파라미터는 클라이언트가 json이나 path parameter로 전달
- json의 경우 메소드 매개변수로 바로 받을 수도 있고 dto를 따로 정의해서 dto객체로 받을 수 있음
### 코드
```java
@RestController // 컨트롤러 계층
@RequiredArgsConstructor
@RequestMapping(path="/todo/") // /todo/ 의 하위 주소에 대한 컨트롤러임 -> /todo/list 를 list 로만 표현
public class TodoController {
    // 서비스 주입
    private final TodoService todoService;

    // 매핑할 하위 주소가 없어서 비워둠
    @PostMapping // http 메소드 지정 @<http_method_type>Mapping
    // 리턴타입은 ResponseEntity<T>
    // 매개변수를 request body를 통해서 받으려면 @RequestBody
    // dto를 받으려면 dto 클래스를 타입으로 명시해줌
    public ResponseEntity<Void> createTodo(@RequestBody TodoCreateRequest request) throws Exception {
        Long todoId = todoService.createTodo(request.getContent(), request.getUid());
        // 리턴 값으로 아무것도 반환하지 않지만 헤더에 URI를 실어서 보냄
        // 동시에 201 Created 를 전달
        return ResponseEntity.created(URI.create("/todo/" + todoId)).build();
    }

    // path parameter를 사용함. 매개변수 이름을 중괄호로 감싸서 넣음
    @DeleteMapping("/{todoId}")
    // todoId는 path parameter에서 받으므로 @PathVariable, 나머지는 request body에서 받으므로 @RequestBody
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId, @RequestBody Long memberId) throws Exception {
        todoService.deleteTodo(todoId, memberId);
        // 정말 아무것도 리턴하지 않음
        return ResponseEntity.noContent().build();
    }
}
```