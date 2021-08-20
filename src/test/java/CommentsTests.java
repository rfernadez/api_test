import Specifications.RequestSpecifications;
import Specifications.ResponseSpecifications;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Comment;
import model.InvalidComment;
import model.Post;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class CommentsTests extends Base {

    private static String resourcePath = "/v1/post";
    private static String resourcePathCmt = "/v1/comment/";
    private static String GetAllCmmts = "/v1/comments";
    private static String GetInvalidCmt = "/v1/commentss";
    private static String GetOneCmt = "/v1/comment/";
    private static Integer createdCmt = 0;
    private static Integer createdPost = 0;



    @Test
    public void createPost() {

        Post testPost = new Post("hola", "examen");

        Response response= given()
                .spec(RequestSpecifications.generateToken())
                .body(testPost)
                .when()
                .post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdPost = jsonPathEvaluator.get("id");

    }

    @Test
    public void createComment() {
    createPost();
        Comment testComment = new Comment("Rebeca", "hola");

         Response response= given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .body(testComment)
                .when()
                .post(resourcePathCmt + createdPost.toString());

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdCmt = jsonPathEvaluator.get("id");

    }

    @Test
    public void Create_Comment_Successful(){
        createPost();
        Comment testComment = new Comment("Rebeca", "hola");

        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .body(testComment)
                .post(resourcePathCmt + createdPost.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Comment created"));
    }
   @Test
    public void Create_Comment_UnSuccessful(){
        createPost();
        InvalidComment testComment = new InvalidComment("Rebeca", "hola");

        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .body(testComment)
                .post(resourcePathCmt + createdPost.toString())
                .then()
                .log().all()
                .statusCode(406)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Invalid form"));
    }
    @Test
    public void Delete_Comment_Successful(){
        createPost();
        createComment();
        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .delete(resourcePathCmt + createdPost.toString() + "/"+ createdCmt.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Comment deleted"));
    }
    @Test
    public void Delete_Comment_UnSuccessful(){

        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .delete(resourcePathCmt + createdPost.toString() + "/"+ createdCmt.toString())
                .then()
                .log().all()
                .statusCode(406)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Comment could not be deleted"));
    }
    @Test
    public void Test_GET_Comments(){
    createPost();
        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .get(GetAllCmmts+ "/"+ createdPost.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec());
    }

    @Test
    public void Test_GET_Comments_Invalid(){

        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .get(GetInvalidCmt + "/"+ createdPost.toString())
                .then()
                .log().all()
                .statusCode(404)
                .spec(ResponseSpecifications.htmlSpec());
    }

    @Test
    public void Test_GET_Comment(){
    createPost();
    createComment();
        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .get(GetOneCmt + createdPost.toString()+ "/"+ createdCmt.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec());
    }

    @Test
    public void Test_GET_Invalid_Comment(){

        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .get(GetOneCmt + createdPost.toString()+ "/"+ createdCmt.toString())
                .then()
                .log().all()
                .statusCode(404)
                .spec(ResponseSpecifications.defaultSpec())
                .body("Message", Matchers.equalTo("Comment not found"));
    }


    @Test
    public void Update_Comment(){
        createPost();
        createComment();
        Comment testComment = new Comment("Juan", "hola");
        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .body(testComment)
                .put(resourcePathCmt + createdPost.toString() + "/"+ createdCmt.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message",Matchers.equalTo("Comment updated"));
    }
    @Test
    public void Update_Invalid_Comment(){
        createPost();
        createComment();
        Comment testComment = new Comment("Juan", "hola");

        given()
                .spec(RequestSpecifications.useBasicAuthentication())
                .body(testComment)
                .put(resourcePathCmt + "10000" + "/"+ createdCmt.toString())
                .then()
                .log().all()
                .statusCode(406)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message",Matchers.equalTo("Comment could not be updated"));
    }

}
