package com.example.tryit.ui.searchResults;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tryit.R;
import com.example.tryit.models.Recipe;
import com.example.tryit.ui.generatedrecipes.RecyclerViewAdapterIngredient;
import com.example.tryit.ui.generatedrecipes.RecyclerViewAdapterSearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class SearchResultsFragment extends Fragment {
    View root;

    private TextView ingredients_list;
    private RecyclerView myrv;
    private JSONArray testArr;
    private List<Recipe> lstRecipe = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search_results, container, false);

        ingredients_list= root.findViewById(R.id.ingredients_names_list);
        progressBar = root.findViewById(R.id.progressbar3);
        String searchText=getStringFromList(RecyclerViewAdapterIngredient.ingredientsList);
        ingredients_list.setText(searchText);
        getResults(searchText);

        return root;
    }

    private void getResults(String searchText) {
        myrv = root.findViewById(R.id.recycleview_ingredients_search_result);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        String URL = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="+searchText+"&number=30&instructionsRequired=true&apiKey=c8a1799a2c8b46049448ec183da2239b";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    testArr = response;
                    Log.i("the res is:", String.valueOf(testArr));
                    for (int i = 0; i < testArr.length(); i++) {
                        JSONObject jsonObject1;
                        jsonObject1 = testArr.getJSONObject(i);
                        lstRecipe.add(new Recipe(jsonObject1.optString("name"),NULL,NULL,jsonObject1.optString("id"),jsonObject1.optString("image")));
                    }
                    progressBar.setVisibility(View.GONE);
                    RecyclerViewAdapterSearchResult myAdapter = new RecyclerViewAdapterSearchResult(getContext(), lstRecipe);
                    myrv.setAdapter(myAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("the res is error:", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private String getStringFromList(List<String> ingredientsList) {
        StringBuilder result= new StringBuilder(ingredientsList.get(0));
        for (int i=1;i < ingredientsList.size();i++)
        {
            result.append(", ").append(ingredientsList.get(i));
        }
        return result.toString();
    }

}