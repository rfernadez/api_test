import Specifications.RequestSpecifications;
import Specifications.ResponseSpecifications;
import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Article;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ArticleTests extends Base {

    private static String resourcePath = "/v1/article";

    private static Integer createdArticle = 0;

    @BeforeGroups("create_article")
    public void createArticle(){

        Article testArticle = new Article(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        Response response = given()
                .spec(RequestSpecifications.generateToken())
                .body(testArticle)
                .post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdArticle = jsonPathEvaluator.get("id");

    }

    @Test
    public void Test_Creat_Article_Success(){

        Article testArticle = new Article(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecifications.generateToken())
                .body(testArticle)
                .post(resourcePath)
                .then()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec());
    }

    @Test(groups = "create_article")
    public void Test_Delete_Article_Success(){

        given()
                .spec(RequestSpecifications.generateToken())
                .delete(resourcePath + "/" + createdArticle.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecifications.defaultSpec());
    }

    @Test
    public void Test_Invalid_Token_Cant_Create_New_Articles(){

        Article testArticle = new Article(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecifications.generateFakeToken())
                .body(testArticle)
                .post(resourcePath)
                .then()
                .statusCode(401)
                .spec(ResponseSpecifications.defaultSpec());
    }



}
