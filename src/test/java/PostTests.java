import Specifications.RequestSpecifications;
import Specifications.ResponseSpecifications;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.InvalidPost;
import model.Post;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostTests extends Base {

    private static String resourcePath = "/v1/post";
    private static String GetAllPosts = "/v1/posts";
    private static String GetInvalidPosts = "/v1/postss";
    private static String GetOnePost = "/v1/post/";
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
    public void Create_Post_Successful(){

        Post testPost = new Post("hola", "examen");

        given()
                .spec(RequestSpecifications.generateToken())
                .body(testPost)
                .post(resourcePath)
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Post created"));
    }

    @Test
    public void Create_Post_UnSuccessful(){

        InvalidPost testPost = new InvalidPost("hola", "examen");

        given()
                .spec(RequestSpecifications.generateToken())
                .body(testPost)
                .post(resourcePath)
                .then()
                .log().all()
                .statusCode(406)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Invalid form"));
    }

    @Test
    public void Delete_Post_Successful(){
        createPost();
        given()
                .spec(RequestSpecifications.generateToken())
                .delete("/v1/post/" + createdPost.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Post deleted"));
    }
    @Test
    public void Delete_Post_UnSuccessful(){

        given()
                .spec(RequestSpecifications.generateToken())
                .delete("/v1/post/" + createdPost.toString())
                .then()
                .log().all()
                .statusCode(406)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message", Matchers.equalTo("Post could not be deleted"));
    }
    @Test
    public void Test_GET_Posts(){

        given()
                .spec(RequestSpecifications.generateToken())
                .get(GetAllPosts)
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec());
    }

    @Test
    public void Test_GET_Posts_Invalid(){

        given()
                .spec(RequestSpecifications.generateToken())
                .get(GetInvalidPosts)
                .then()
                .log().all()
                .statusCode(404)
                .spec(ResponseSpecifications.htmlSpec());
    }

    @Test
    public void Test_GET_Post(){
    createPost();
        given()
                .spec(RequestSpecifications.generateToken())
                .get(GetOnePost + createdPost.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec());
    }

    @Test
    public void Test_GET_Invalid_Post(){

        given()
                .spec(RequestSpecifications.generateToken())
                .get(GetOnePost + createdPost.toString())
                .then()
                .log().all()
                .statusCode(404)
                .spec(ResponseSpecifications.defaultSpec())
                .body("Message", Matchers.equalTo("Post not found"));
    }

    @Test
    public void Update_Post(){
        createPost();
        Post testPost = new Post("nuevo titulo","nuevo contenido" );
        given()
                .spec(RequestSpecifications.generateToken())
                .body(testPost)
                .put(resourcePath +"/" + createdPost.toString())
                .then()
                .log().all()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message",Matchers.equalTo("Post updated"));
    }
    @Test
    public void Update_Invalid_Post(){
        createPost();
        Post testPost = new Post("nuevo titulo","nuevo contenido" );
        given()
                .spec(RequestSpecifications.generateToken())
                .body(testPost)
                .put(resourcePath +"/" + "10000")
                .then()
                .log().all()
                .statusCode(406)
                .spec(ResponseSpecifications.defaultSpec())
                .body("message",Matchers.equalTo("Post could not be updated"));
    }

}
