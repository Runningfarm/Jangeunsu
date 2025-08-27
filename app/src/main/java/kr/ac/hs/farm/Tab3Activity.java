package kr.ac.hs.farm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ProgressBar;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;
import android.util.Log;
import android.view.View;

public class Tab3Activity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    // 퀘스트 개수만 바꾸면 됨!
    private static final int QUEST_COUNT = 23;

    private ProgressBar[] progressBars;
    private Button[] claimButtons;
    private ImageView[] boxImages;

    private ImageView imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
        Log.d("퀘스트응답", "Tab3Activity onCreate 진입!");

        View rootView = findViewById(R.id.root_quest);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        progressBars = new ProgressBar[QUEST_COUNT];
        claimButtons = new Button[QUEST_COUNT];
        boxImages   = new ImageView[QUEST_COUNT];


        initQuestViews();

        imagePreview = findViewById(R.id.imagePreview);
        Button buttonTakePhoto = findViewById(R.id.buttonTakePhoto);

        buttonTakePhoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // 하단 탭
        findViewById(R.id.tab1Button).setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.tab2Button).setOnClickListener(view -> startActivity(new Intent(this, Tab2Activity.class)));
        findViewById(R.id.tab3Button).setOnClickListener(view -> startActivity(new Intent(this, Tab3Activity.class)));
        findViewById(R.id.tab4Button).setOnClickListener(view -> startActivity(new Intent(this, Tab4Activity.class)));
        findViewById(R.id.tab6Button).setOnClickListener(view -> startActivity(new Intent(this, Tab6Activity.class)));

        loadQuestProgressFromServer();
    }


    private void initQuestViews() {
        for (int i = 0; i < QUEST_COUNT; i++) {
            int idx = i + 1;

            int progId = getResIdByName("progressQuest" + idx);
            int btnId  = getResIdByName("btnClaim" + idx);
            int boxId  = getResIdByName("boxReward" + idx);

            if (progId != 0) progressBars[i] = findViewById(progId);
            if (btnId  != 0) claimButtons[i] = findViewById(btnId);
            if (boxId  != 0) boxImages[i]    = findViewById(boxId);

            // 버튼 리스너
            final int questNumber = idx;
            if (claimButtons[i] != null) {
                claimButtons[i].setOnClickListener(v -> claimQuest(questNumber));
            }
        }
    }

    private int getResIdByName(String name) {
        return getResources().getIdentifier(name, "id", getPackageName());
    }

    private void loadQuestProgressFromServer() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<QuestProgressResponse> call = api.getQuestProgress("Bearer " + token);
        call.enqueue(new Callback<QuestProgressResponse>() {
            @Override
            public void onResponse(Call<QuestProgressResponse> call, Response<QuestProgressResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateQuestUI(response.body().getQuests());
                } else {
                    Toast.makeText(Tab3Activity.this, "퀘스트 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuestProgressResponse> call, Throwable t) {
                Toast.makeText(Tab3Activity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void claimQuest(int questNumber) {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        ClaimQuestRequest request = new ClaimQuestRequest(questNumber);
        Call<RunResultResponse> call = api.claimQuest("Bearer " + token, request);

        call.enqueue(new Callback<RunResultResponse>() {
            @Override
            public void onResponse(Call<RunResultResponse> call, Response<RunResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RunResultResponse result = response.body();
                    if (result.isSuccess()) {
                        int reward = result.getReward();
                        Toast.makeText(Tab3Activity.this, "보상으로 먹이 " + reward + "개를 받았습니다!", Toast.LENGTH_SHORT).show();


                        int index = questNumber - 1;
                        ImageView box = (index >= 0 && index < boxImages.length) ? boxImages[index] : null;
                        if (box == null) {
                            int boxId = getResIdByName("boxReward" + questNumber);
                            if (boxId != 0) box = findViewById(boxId);
                        }
                        if (box != null) {
                            box.setImageResource(R.drawable.box_opened);
                            Animation fadeIn = AnimationUtils.loadAnimation(Tab3Activity.this, R.anim.fade_open);
                            box.startAnimation(fadeIn);
                        }

                        Intent intent = new Intent(Tab3Activity.this, MainActivity.class);
                        intent.putExtra("reward", reward);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Tab3Activity.this, "보상을 받을 수 없는 상태입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Tab3Activity.this, "퀘스트 중복 보상 받기 불가", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RunResultResponse> call, Throwable t) {
                Toast.makeText(Tab3Activity.this, "서버 연결 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateQuestUI(List<QuestProgressResponse.Quest> quests) {
        if (quests == null) return;

        int count = Math.min(quests.size(), QUEST_COUNT);
        for (int i = 0; i < count; i++) {
            QuestProgressResponse.Quest q = quests.get(i);
            ProgressBar bar = progressBars[i];
            Button btn = claimButtons[i];

            if (bar != null) {
                double target = q.getTarget();
                int percent = (target > 0) ? (int) ((q.getProgress() / target) * 100) : 0;
                if (percent < 0) percent = 0;
                if (percent > 100) percent = 100;
                bar.setProgress(percent);
            }
            if (btn != null) {
                btn.setEnabled(q.isCompleted());
            }
        }

        for (QuestProgressResponse.Quest q : quests) {
            if ("kcal".equals(q.getType())) {
                int progress = (int) q.getProgress();

                // 100 kcal → 7번
                if (progress >= 100 && 6 < QUEST_COUNT) {
                    if (progressBars[6] != null) progressBars[6].setProgress(100);
                    if (claimButtons[6] != null) claimButtons[6].setEnabled(true);
                }
                // 200 kcal → 8번
                if (progress >= 200 && 7 < QUEST_COUNT) {
                    if (progressBars[7] != null) progressBars[7].setProgress(100);
                    if (claimButtons[7] != null) claimButtons[7].setEnabled(true);
                }
                // 400 kcal → 9번
                if (progress >= 400 && 8 < QUEST_COUNT) {
                    if (progressBars[8] != null) progressBars[8].setProgress(100);
                    if (claimButtons[8] != null) claimButtons[8].setEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            if (imageBitmap != null && imagePreview != null) {
                imagePreview.setImageBitmap(imageBitmap);
            }
        }
    }
}
