package com.example.tryit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tryit.models.Ingredient;
import com.example.tryit.ui.generatedrecipes.RecyclerViewAdapterRecipeIngredient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import android.widget.Button;

public class Recipe_Activity extends AppCompatActivity {

    private TextView title, instructions;
    private ImageView img, vegeterian;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private JSONArray ingredientsArr;
    private List<Ingredient> ingredientsLst = new ArrayList<Ingredient>();
    private RecyclerView myrv;
    private Button backButton;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        final Intent intent = getIntent();
        final String recipeId = Objects.requireNonNull(intent.getExtras()).getString("id");
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();
//        mRootRef = FirebaseDatabase.getInstance().getReference().child(uid).child(recipeId); //this line causes error and variable is not used
        img = findViewById(R.id.recipe_img);
        title = findViewById(R.id.recipe_title);
        instructions = findViewById(R.id.recipe_instructions);

        //needed so the app doesn't crash
        backButton = findViewById(R.id.rec_activity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getRecipeData(recipeId);

        myrv = findViewById(R.id.recipe_ingredients_rv);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getRecipeData(final String recipeId) {
        String URL = " https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=c8a1799a2c8b46049448ec183da2239b";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            try {
                                Picasso.get().load((String) response.get("image")).into(img);
                            }
                            catch (Exception e){
                                img.setImageResource(R.drawable.nopicture);
                            }
                            title.setText((String) response.get("title"));

                            try{
                                if(response.get("instructions").equals("")){
                                    throw new Exception("No Instructions");
                                }
                                else
                                    instructions.setText(Html.fromHtml((String) response.get("instructions")));
                            }
                            catch(Exception e){
                                String msg= "Unfortunately, the recipe you were looking for not found, to view the original recipe click on the link below:" + "<a href="+response.get("spoonacularSourceUrl")+">"+response.get("spoonacularSourceUrl")+"</a>";
                                instructions.setMovementMethod(LinkMovementMethod.getInstance());
                                instructions.setText(Html.fromHtml(msg));
                            }
                            ingredientsArr = (JSONArray) response.get("extendedIngredients");
                            for (int i = 0; i < ingredientsArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = ingredientsArr.getJSONObject(i);
                                ingredientsLst.add(new Ingredient(jsonObject1.optString("originalString"),jsonObject1.optString("unit"),jsonObject1.optInt("amount"),jsonObject1.optString("image")));
                            }
                            RecyclerViewAdapterRecipeIngredient myAdapter = new RecyclerViewAdapterRecipeIngredient(getApplicationContext(), ingredientsLst);
                            myrv.setAdapter(myAdapter);
                            myrv.setItemAnimator(new DefaultItemAnimator());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}