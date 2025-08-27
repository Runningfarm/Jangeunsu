<h1>8/22 수정사항</h1>
index.js

```
app.get("/myfarm", verifyToken, async (req, res) => {
안에
res.json({
      success: true,
      message: "농장 정보 가져오기 성공!",
      id: user.id,
      weight: user.weight,
      totalDistance: user.totalDistance,
      totalFood: user.totalFood,
      totalRunTime: user.totalRunTime || 0,
```
이 부분
```
res.json({
      success: true,
      message: "농장 정보 가져오기 성공!",
      id: user.id,
      weight: user.weight,
      totalDistance: user.totalDistance || 0,
      totalFood: user.totalFood,
      totalCalories: user.totalCalories || 0,
      totalRunTime: user.totalRunTime || 0,
```
이렇게 수정


app.post("/run/complete", verifyToken, async (req, res) => {
안에
```
res.json({
      success: true,
      message: "런닝 결과 저장+퀘스트 반영 완료!",
      quests: user.quests,
      totalDistance: user.totalDistance || 0,
      totalFood: user.totalFood,
      totalCalories: user.totalCalories || 0,
      totalRunTime: user.totalRunTime || 0,
```
이렇게 수정


app.post("/login", async (req, res) => {
안에
```
res.json({
      success: true,
      message: "로그인 성공!",
      token,
      id: user.id,
      name: user.name,
      weight: user.weight,
      totalDistance: user.totalDistance|| 0,
      totalFood: user.totalFood,
      totalCalories: user.totalCalories || 0,
      totalRunTime: user.totalRunTime || 0,
      questsCompleted: user.questsCompleted,
```
이렇게 수정


activity_edit_profile.xml
```
<!-- [신규] 총 누적 달리기 시간 카드 -->
        <LinearLayout
            android:id="@+id/totalTimeCardProfile"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:background="#EAF6E9"
            android:padding="16dp"
            android:elevation="2dp"
            android:layout_marginBottom="16dp"
            android:layout_marginTop="4dp"
            android:backgroundTint="#EAF6E9">

            <TextView
                android:id="@+id/tvTotalLabelProfile"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="총 누적 달리기 시간"
                android:textColor="#5D7755"
                android:textSize="14sp"
                android:textStyle="bold"
                android:fontFamily="@font/nanumgothic_regular" />

            <TextView
                android:id="@+id/tvTotalRunTimeProfile"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="6dp"
                android:text="00:00:00"
                android:textColor="#347EBF"
                android:textSize="22sp"
                android:textStyle="bold"
                android:fontFamily="@font/nanumgothic_regular" />
        </LinearLayout>
```
밑에
```
<!-- [신규] 총 누적 거리 카드 -->
        <LinearLayout
            android:id="@+id/totalDistanceCardProfile"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:background="#EAF6E9"
            android:padding="16dp"
            android:elevation="2dp"
            android:layout_marginBottom="16dp"
            android:backgroundTint="#EAF6E9">

            <TextView
                android:id="@+id/tvDistanceLabelProfile"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="총 누적 거리"
                android:textColor="#5D7755"
                android:textSize="14sp"
                android:textStyle="bold"
                android:fontFamily="@font/nanumgothic_regular" />

            <TextView
                android:id="@+id/tvTotalDistanceProfile"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="6dp"
                android:text="0.00 km"
                android:textColor="#347EBF"
                android:textSize="22sp"
                android:textStyle="bold"
                android:fontFamily="@font/nanumgothic_regular" />
        </LinearLayout>

        <!-- [신규] 총 누적 칼로리 카드 -->
        <LinearLayout
            android:id="@+id/totalCaloriesCardProfile"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:background="#EAF6E9"
            android:padding="16dp"
            android:elevation="2dp"
            android:layout_marginBottom="16dp"
            android:backgroundTint="#EAF6E9">

            <TextView
                android:id="@+id/tvCaloriesLabelProfile"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="총 누적 칼로리"
                android:textColor="#5D7755"
                android:textSize="14sp"
                android:textStyle="bold"
                android:fontFamily="@font/nanumgothic_regular" />

            <TextView
                android:id="@+id/tvTotalCaloriesProfile"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="6dp"
                android:text="0 kcal"
                android:textColor="#347EBF"
                android:textSize="22sp"
                android:textStyle="bold"
                android:fontFamily="@font/nanumgothic_regular" />
        </LinearLayout>
```
추가


LoginResponse.java
```
private double totalDistance;
private int totalCalories;
```
추가
```
public double getTotalDistance() { return totalDistance; }
public int getTotalCalories() { return totalCalories; }
```
추가


EditProfileActivity.java
```
import retrofit2.Response;
```
밑에
```
import retrofit2.http.Header;
```
추가
<br>
```
private TextView tvTotalRunTimeProfile;
```
밑에
```
private TextView tvTotalDistanceProfile;
private TextView tvTotalCaloriesProfile;
```
추가
<br>
```
tvTotalRunTimeProfile = findViewById(R.id.tvTotalRunTimeProfile);
```
밑에
```
tvTotalDistanceProfile  = findViewById(R.id.tvTotalDistanceProfile);
tvTotalCaloriesProfile  = findViewById(R.id.tvTotalCaloriesProfile);
```
추가
```
long totalRunSecs = pref.getLong("total_run_time_seconds", 0L);
tvTotalRunTimeProfile.setText(formatSecondsToHMS(totalRunSecs));
```
이 부분을
```
long totalRunSecs = pref.getLong("total_run_time_seconds", 0L);
        float totalDistance = pref.getFloat("total_distance", 0f);
        long totalCalories = pref.getLong("total_calories", 0L);
        tvTotalRunTimeProfile.setText(formatSecondsToHMS(totalRunSecs));
        tvTotalDistanceProfile.setText(totalDistance + " km");
        tvTotalCaloriesProfile.setText(totalCalories + " kcal");
```
이렇게 수정


User.js
```
// 100시간
      {
        type: "time_total",
        target: 100 * 3600,
        progress: 0,
        completed: false,
        reward: 25,
        claimed: false,
      },
```
밑에
```
// 누적거리 퀘스트
      // 100km
      {
        type: "distance_total",
        target: 100,
        progress: 0,
        completed: false,
        reward: 15,
      },
      // 500km
      {
        type: "distance_total",
        target: 500,
        progress: 0,
        completed: false,
        reward: 30,
      },
      // 1000km
      {
        type: "distance_total",
        target: 1000,
        progress: 0,
        completed: false,
        reward: 50,
      },
      // 누적 칼로리 퀘스트
      // 10,000 kcal
      {
        type: "calorie_total",
        target: 10000,
        progress: 0,
        completed: false,
        reward: 20,
      },
      // 50,000 kcal
      {
        type: "calorie_total",
        target: 50000,
        progress: 0,
        completed: false,
        reward: 40,
      },
      // 100,000 kcal
      {
        type: "calorie_total",
        target: 100000,
        progress: 0,
        completed: false,
        reward: 70,
      },
```
추가


index.js
```
// 총 누적 달리기 시간(초) 누적
    if (typeof time === "number" && time > 0) {
      user.totalRunTime = (user.totalRunTime || 0) + time;
    }
```
밑에
```
// 누적시간 퀘스트(type: "time_total") 진행/완료 처리 (단위: 초)
    user.quests.forEach((q) => {
      if (q.type === "time_total" && !q.completed) {
        q.progress += user.totalRunTime;
        if (q.progress >= q.target) {
          q.progress = q.target;
          q.completed = true;
          q.completedAt = new Date();
        }
      }
    });

    // 누적 거리 퀘스트 진행도
    user.quests.forEach((q) => {
      if (q.type === "distance_total" && !q.completed) {
        q.progress = user.totalDistance;
        if (q.progress >= q.target) {
          q.progress = q.target;
          q.completed = true;
          q.completedAt = new Date();
        }
      }
    });

    // 누적 칼로리 퀘스트 진행도
    user.quests.forEach((q) => {
      if (q.type === "calorie_total" && !q.completed) {
        q.progress = user.totalCalories;
        if (q.progress >= q.target) {
          q.progress = q.target;
          q.completed = true;
          q.completedAt = new Date();
        }
      }
    });
```
이렇게 수정 및 추가

Tab3Activity.java
```
private ProgressBar[] progressBars = new ProgressBar[17];
```
를
```
private ProgressBar[] progressBars = new ProgressBar[23];
```
로 수정

```
private Button[] claimButtons = new Button[17];
```
를
```
private Button[] claimButtons = new Button[23];
```
로 수정

```
progressBars[17] = findViewById(R.id.progressQuest18);
        progressBars[18] = findViewById(R.id.progressQuest19);
        progressBars[19] = findViewById(R.id.progressQuest20);
        progressBars[20] = findViewById(R.id.progressQuest21);
        progressBars[21] = findViewById(R.id.progressQuest22);
        progressBars[22] = findViewById(R.id.progressQuest23);
```
추가

```
claimButtons[17] = findViewById(R.id.btnClaim18);
        claimButtons[18] = findViewById(R.id.btnClaim19);
        claimButtons[19] = findViewById(R.id.btnClaim20);
        claimButtons[20] = findViewById(R.id.btnClaim21);
        claimButtons[21] = findViewById(R.id.btnClaim22);
        claimButtons[22] = findViewById(R.id.btnClaim23);
```
추가

```
for (int i = 0; i < Math.min(quests.size(), 17); i++) {
```
를
```
for (int i = 0; i < Math.min(quests.size(), 23); i++) {
```
로 수정


activity_tab3.xml
퀘스트 6개 추가
```
 <!-- 18번 퀘스트 -->
                    <androidx.cardview.widget.CardView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginBottom="12dp"
                        app:cardCornerRadius="14dp"
                        app:cardElevation="6dp"
                        android:background="#FFFFFF">

                        <LinearLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:orientation="horizontal"
                            android:padding="16dp"
                            android:gravity="center_vertical">

                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_marginEnd="16dp"
                                android:src="@drawable/trophy"
                                android:contentDescription="보상 이미지" />

                            <LinearLayout
                                android:layout_width="0dp"
                                android:layout_height="wrap_content"
                                android:layout_weight="1"
                                android:orientation="vertical">

                                <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="누적 100km 완료 퀘스트"
                                    android:textColor="#5D7755"
                                    android:textSize="16sp"
                                    android:textStyle="bold"
                                    android:fontFamily="@font/gowundodum_regular" />

                                <FrameLayout
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content"
                                    android:layout_marginTop="8dp">

                                    <ProgressBar
                                        android:id="@+id/progressQuest18"
                                        style="@android:style/Widget.DeviceDefault.Light.ProgressBar.Horizontal"
                                        android:layout_width="match_parent"
                                        android:layout_height="12dp"
                                        android:max="100"
                                        android:progress="30"
                                        android:progressDrawable="@drawable/progress_green_custom" />

                                    <ImageView
                                        android:id="@+id/boxReward18"
                                        android:layout_width="24dp"
                                        android:layout_height="24dp"
                                        android:layout_gravity="end|center_vertical"
                                        android:src="@drawable/box_locked"
                                        android:contentDescription="보상 상자" />
                                </FrameLayout>
                            </LinearLayout>

                            <Button
                                android:id="@+id/btnClaim18"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:text="보상받기"
                                android:enabled="false"
                                android:textColor="#5D7755"
                                android:backgroundTint="#FFF7D1"
                                android:textStyle="bold"
                                android:layout_marginStart="12dp"
                                android:elevation="2dp" />
                        </LinearLayout>
                    </androidx.cardview.widget.CardView>

                    <!-- 19번 퀘스트 -->
                    <androidx.cardview.widget.CardView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginBottom="12dp"
                        app:cardCornerRadius="14dp"
                        app:cardElevation="6dp"
                        android:background="#FFFFFF">

                        <LinearLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:orientation="horizontal"
                            android:padding="16dp"
                            android:gravity="center_vertical">

                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_marginEnd="16dp"
                                android:src="@drawable/trophy"
                                android:contentDescription="보상 이미지" />

                            <LinearLayout
                                android:layout_width="0dp"
                                android:layout_height="wrap_content"
                                android:layout_weight="1"
                                android:orientation="vertical">

                                <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="누적 500km 완료 퀘스트"
                                    android:textColor="#5D7755"
                                    android:textSize="16sp"
                                    android:textStyle="bold"
                                    android:fontFamily="@font/gowundodum_regular" />

                                <FrameLayout
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content"
                                    android:layout_marginTop="8dp">

                                    <ProgressBar
                                        android:id="@+id/progressQuest19"
                                        style="@android:style/Widget.DeviceDefault.Light.ProgressBar.Horizontal"
                                        android:layout_width="match_parent"
                                        android:layout_height="12dp"
                                        android:max="100"
                                        android:progress="30"
                                        android:progressDrawable="@drawable/progress_green_custom" />

                                    <ImageView
                                        android:id="@+id/boxReward19"
                                        android:layout_width="24dp"
                                        android:layout_height="24dp"
                                        android:layout_gravity="end|center_vertical"
                                        android:src="@drawable/box_locked"
                                        android:contentDescription="보상 상자" />
                                </FrameLayout>
                            </LinearLayout>

                            <Button
                                android:id="@+id/btnClaim19"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:text="보상받기"
                                android:enabled="false"
                                android:textColor="#5D7755"
                                android:backgroundTint="#FFF7D1"
                                android:textStyle="bold"
                                android:layout_marginStart="12dp"
                                android:elevation="2dp" />
                        </LinearLayout>
                    </androidx.cardview.widget.CardView>

                    <!-- 20번 퀘스트 -->
                    <androidx.cardview.widget.CardView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginBottom="12dp"
                        app:cardCornerRadius="14dp"
                        app:cardElevation="6dp"
                        android:background="#FFFFFF">

                        <LinearLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:orientation="horizontal"
                            android:padding="16dp"
                            android:gravity="center_vertical">

                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_marginEnd="16dp"
                                android:src="@drawable/trophy"
                                android:contentDescription="보상 이미지" />

                            <LinearLayout
                                android:layout_width="0dp"
                                android:layout_height="wrap_content"
                                android:layout_weight="1"
                                android:orientation="vertical">

                                <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="누적 1000km 완료 퀘스트"
                                    android:textColor="#5D7755"
                                    android:textSize="16sp"
                                    android:textStyle="bold"
                                    android:fontFamily="@font/gowundodum_regular" />

                                <FrameLayout
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content"
                                    android:layout_marginTop="8dp">

                                    <ProgressBar
                                        android:id="@+id/progressQuest20"
                                        style="@android:style/Widget.DeviceDefault.Light.ProgressBar.Horizontal"
                                        android:layout_width="match_parent"
                                        android:layout_height="12dp"
                                        android:max="100"
                                        android:progress="30"
                                        android:progressDrawable="@drawable/progress_green_custom" />

                                    <ImageView
                                        android:id="@+id/boxReward20"
                                        android:layout_width="24dp"
                                        android:layout_height="24dp"
                                        android:layout_gravity="end|center_vertical"
                                        android:src="@drawable/box_locked"
                                        android:contentDescription="보상 상자" />
                                </FrameLayout>
                            </LinearLayout>

                            <Button
                                android:id="@+id/btnClaim20"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:text="보상받기"
                                android:enabled="false"
                                android:textColor="#5D7755"
                                android:backgroundTint="#FFF7D1"
                                android:textStyle="bold"
                                android:layout_marginStart="12dp"
                                android:elevation="2dp" />
                        </LinearLayout>
                    </androidx.cardview.widget.CardView>

                    <!-- 21번 퀘스트 -->
                    <androidx.cardview.widget.CardView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginBottom="12dp"
                        app:cardCornerRadius="14dp"
                        app:cardElevation="6dp"
                        android:background="#FFFFFF">

                        <LinearLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:orientation="horizontal"
                            android:padding="16dp"
                            android:gravity="center_vertical">

                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_marginEnd="16dp"
                                android:src="@drawable/trophy"
                                android:contentDescription="보상 이미지" />

                            <LinearLayout
                                android:layout_width="0dp"
                                android:layout_height="wrap_content"
                                android:layout_weight="1"
                                android:orientation="vertical">

                                <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="누적 10,000kcal 완료 퀘스트"
                                    android:textColor="#5D7755"
                                    android:textSize="16sp"
                                    android:textStyle="bold"
                                    android:fontFamily="@font/gowundodum_regular" />

                                <FrameLayout
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content"
                                    android:layout_marginTop="8dp">

                                    <ProgressBar
                                        android:id="@+id/progressQuest21"
                                        style="@android:style/Widget.DeviceDefault.Light.ProgressBar.Horizontal"
                                        android:layout_width="match_parent"
                                        android:layout_height="12dp"
                                        android:max="100"
                                        android:progress="30"
                                        android:progressDrawable="@drawable/progress_green_custom" />

                                    <ImageView
                                        android:id="@+id/boxReward21"
                                        android:layout_width="24dp"
                                        android:layout_height="24dp"
                                        android:layout_gravity="end|center_vertical"
                                        android:src="@drawable/box_locked"
                                        android:contentDescription="보상 상자" />
                                </FrameLayout>
                            </LinearLayout>

                            <Button
                                android:id="@+id/btnClaim21"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:text="보상받기"
                                android:enabled="false"
                                android:textColor="#5D7755"
                                android:backgroundTint="#FFF7D1"
                                android:textStyle="bold"
                                android:layout_marginStart="12dp"
                                android:elevation="2dp" />
                        </LinearLayout>
                    </androidx.cardview.widget.CardView>

                    <!-- 22번 퀘스트 -->
                    <androidx.cardview.widget.CardView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginBottom="12dp"
                        app:cardCornerRadius="14dp"
                        app:cardElevation="6dp"
                        android:background="#FFFFFF">

                        <LinearLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:orientation="horizontal"
                            android:padding="16dp"
                            android:gravity="center_vertical">

                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_marginEnd="16dp"
                                android:src="@drawable/trophy"
                                android:contentDescription="보상 이미지" />

                            <LinearLayout
                                android:layout_width="0dp"
                                android:layout_height="wrap_content"
                                android:layout_weight="1"
                                android:orientation="vertical">

                                <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="누적 50,000kcal 완료 퀘스트"
                                    android:textColor="#5D7755"
                                    android:textSize="16sp"
                                    android:textStyle="bold"
                                    android:fontFamily="@font/gowundodum_regular" />

                                <FrameLayout
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content"
                                    android:layout_marginTop="8dp">

                                    <ProgressBar
                                        android:id="@+id/progressQuest22"
                                        style="@android:style/Widget.DeviceDefault.Light.ProgressBar.Horizontal"
                                        android:layout_width="match_parent"
                                        android:layout_height="12dp"
                                        android:max="100"
                                        android:progress="30"
                                        android:progressDrawable="@drawable/progress_green_custom" />

                                    <ImageView
                                        android:id="@+id/boxReward22"
                                        android:layout_width="24dp"
                                        android:layout_height="24dp"
                                        android:layout_gravity="end|center_vertical"
                                        android:src="@drawable/box_locked"
                                        android:contentDescription="보상 상자" />
                                </FrameLayout>
                            </LinearLayout>

                            <Button
                                android:id="@+id/btnClaim22"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:text="보상받기"
                                android:enabled="false"
                                android:textColor="#5D7755"
                                android:backgroundTint="#FFF7D1"
                                android:textStyle="bold"
                                android:layout_marginStart="12dp"
                                android:elevation="2dp" />
                        </LinearLayout>
                    </androidx.cardview.widget.CardView>

                    <!-- 23번 퀘스트 -->
                    <androidx.cardview.widget.CardView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginBottom="12dp"
                        app:cardCornerRadius="14dp"
                        app:cardElevation="6dp"
                        android:background="#FFFFFF">

                        <LinearLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:orientation="horizontal"
                            android:padding="16dp"
                            android:gravity="center_vertical">

                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_marginEnd="16dp"
                                android:src="@drawable/trophy"
                                android:contentDescription="보상 이미지" />

                            <LinearLayout
                                android:layout_width="0dp"
                                android:layout_height="wrap_content"
                                android:layout_weight="1"
                                android:orientation="vertical">

                                <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="누적 100,000kcal 완료 퀘스트"
                                    android:textColor="#5D7755"
                                    android:textSize="16sp"
                                    android:textStyle="bold"
                                    android:fontFamily="@font/gowundodum_regular" />

                                <FrameLayout
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content"
                                    android:layout_marginTop="8dp">

                                    <ProgressBar
                                        android:id="@+id/progressQuest23"
                                        style="@android:style/Widget.DeviceDefault.Light.ProgressBar.Horizontal"
                                        android:layout_width="match_parent"
                                        android:layout_height="12dp"
                                        android:max="100"
                                        android:progress="30"
                                        android:progressDrawable="@drawable/progress_green_custom" />

                                    <ImageView
                                        android:id="@+id/boxReward23"
                                        android:layout_width="24dp"
                                        android:layout_height="24dp"
                                        android:layout_gravity="end|center_vertical"
                                        android:src="@drawable/box_locked"
                                        android:contentDescription="보상 상자" />
                                </FrameLayout>
                            </LinearLayout>

                            <Button
                                android:id="@+id/btnClaim23"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:text="보상받기"
                                android:enabled="false"
                                android:textColor="#5D7755"
                                android:backgroundTint="#FFF7D1"
                                android:textStyle="bold"
                                android:layout_marginStart="12dp"
                                android:elevation="2dp" />
                        </LinearLayout>
                    </androidx.cardview.widget.CardView>
```

build.gradle.kts(Module :app)
```
implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
```
추가

