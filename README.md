# FITIT

###### tags: `android` 

## Introduction <img height="50" src="https://i.imgur.com/Hm7DhKm.png">

* An android application features **pet keeping** and **fitness**.
* The app is desinged for **the elderly**.
* Aim to develop the function of **promoting social interaction** in the future.


| <img src="https://i.imgur.com/d3XWoJ6.jpg "> | <img src="https://i.imgur.com/dv6xvg3.jpg"> | <img src="https://i.imgur.com/wjsHQQu.jpg"> |<img src="https://i.imgur.com/FkjrJ11.jpg"> | <img src="https://i.imgur.com/8pJIDKC.jpg"> | <img src="https://i.imgur.com/8pwRvNc.jpg"> |
| ---------------------------------------------- | -------------------------------------------- | ------------------------------------------- | ---------------------------------------------- | -------------------------------------------- | ------------------------------------------- |

---
 
## :iphone: Download 
<img src="https://i.imgur.com/rla29AH.png" >

**`current APK`：`v1.20`**

---

## :hammer_and_wrench: Develop Environment
* Android Studio 4.1.1
* Build #AI-201.8743.12.41.6953283, built on November 5, 2020
* Runtime version: 1.8.0_242-release-1644-b3-6915495 x86_64

---

## :pushpin: Configuration
### Architecture
* Feature:
    ![](https://i.imgur.com/zL0CCPU.png)
    
* Java Structure:
    ![](https://i.imgur.com/vjY1F8I.png)

### Document
#### *Class：*
| Main Class | Description |
| :--------: | -------- | 
| `MainActivity` | 程式進入點|
| `Register` | 註冊 |
| `NewHome` | 建立滑動頁面 |
| `PetFragment` | 寵物介面 | 
| `ExerciseFragment` | 運動介面 | 
| `ReportFragment` | 週報介面 |
| `Attribute` | 寵物資訊介面 | 
| `Exercise` | 運動畫面 | 

| Function Class | Description |
| :--------: | -------- | 
| `DBHelper` | 建立資料庫 |
| `AccountInfo` | 用來操作資料庫中的帳戶屬性（包含名稱和密碼） |
| `PetInfo` | 用來操作資料庫中的寵物屬性（各項運動的運動總次數） |
| `DiaryInfo` | 用來操作資料庫中的日誌屬性（ 每日各項運動的運動總次數） |
| `WeekInfo` | 用來操作每週各項運動的運動總次數（由`DiaryInfo`計算） | 

#### *function：*
* `MainActivity`：
    | Function | Description |
    | :--------: | -------- | 
    | `findObject` | 找尋物件 |
    | `getAccount` | 找到用戶的帳戶資訊 |
    | `login` | 自動登入，或提示尚未建立帳號 |
    | `invisibleRegister` | 在用戶建立完帳號後，下次進入畫面將不在顯示註冊按鈕。 |
    | `buttonClickEvent` | 控制進入註冊畫面以及登入帳號 | 
    
* `Register`：
    | Function | Description |
    | :--------: | -------- | 
    | `findObject` | 找尋物件 |
    | `register` | 新增帳戶資料至資料庫 |
    | `checkRegister` | 檢查是否已有賬戶存在（避免重複註冊） |
    | `buttonClickEvent` | 控制註冊帳號以及返回登入畫面 | 
    
* `NewHome`：
    | Function | Description |
    | :--------: | -------- | 
    | `getAccountName` | 取得登入者名字 |
    | `prepareViewPager` | 建立`ViewPager` |
    | **`class`** `MainAdapter` | 控制`Fragment Page` | 
    
* `PetFragment`：
    | Function | Description |
    | :--------: | -------- | 
    | `findObject` | 找尋物件 |
    | `setUsername` | 設定用戶名字 |
    | `changeDialog` | 變換對話框內容 |
    | `changeImage` | 寵物進化後會變換圖片 | 
    | `setLevel` | 設定等級 |
    | `updatePetInfo` | 更新`PetInfo`並取得最新`PetInfo` |
    | `setCloseness` | 設定親密度 |
    | `getCurrentDate` | 獲得當前的日期 | 
    | `getDiaryList` | 獲取`DiaryInfo`，若為空則插入一筆空資料 |
    | `newDiaryInfo` | 更新`DiaryInfo`，包含`getDiaryList`以及`fillEmptyDiary`|
    | `fillEmptyDiary` | 將使用者未登入的天數填滿`0`的紀錄 |
    | `check_last` | 檢查是否為每月最後一天，確保日期格式一致 | 
    | `buttonClickEvent` | 控制進入寵物資訊（`Attribute`） |
    | `insert` | 插入測試資料（新帳號則不在`getDiaryList`呼叫） |
    
* `ExerciseFragment`：
    | Function | Description |
    | :--------: | -------- | 
    | `findObject` | 找尋物件 |
    | `updateExerciseNum` | 更新今日各項運動次數（從`DiaryInfo`讀取） |
    | `changeColor` | 變換按鈕顏色（依不同完成次數） |
    | `getCurrentDate` | 獲得當前的日期 | 
    | `ClickBtnEvent` | 控制進入運動畫面 |
    
* `ReportFragment`：
    | Function | Description |
    | :--------: | -------- | 
    | `findObject` | 找尋物件 |
    | `setInit` | 取得所有運動資訊，並`generateReport` |
    | `getWeekList` | 計算每週的運動資訊 |
    | `getReportDateList` | 計算每週的日期起訖 | 
    | `getDateFormat` | 變換日期樣式 |
    | `getDateLabel` | 取得日期的標籤 |
    | `buttonClickEvent` | 控制切換不同週次 |
    | `generateReport` | 產生週報，包含圖表和運動建議 |
    | `setReportCard` | 設定運動建議項目 | 
    | `setBarChart` | 設定長條圖外觀樣式 |
    | `getBarData` | 設定所有長條圖內容的資訊與樣式 |
    | `getChartData` | 設定每一筆長條圖資料 |
    | `getLabels` | 設定圖表`y`軸標籤 | 
    | **`class`** `YLabelFormat` | 設定`y`軸標籤格式 |
    
* `Attritube`：
    | Function | Description |
    | :--------: | -------- | 
    | `findObject` | 找尋物件 |
    | `setValue` | 設定當前型態 |
    | `setEvolutionProgress` | 設定進度條 |
    | `updateInfo` | 更新`PetInfo`並取得最新`PetInfo` |
    | `buttonClickEvent` | 控制返回`PetFragment` | 
    | `evolutionClickEvent` | 控制進入註冊畫面以及登入帳號 | 
    
* `Exercise`：
    | Function | Description |
    | :--------: | -------- | 
    | `findObject` |  |
    | `onKeyDown` |  |
    | `clickEvent` |  |
    | `setLayout` |  | 
    | `timerPause` |  |
    | `timerResume` |  |
    | `countDown` |  |
    | `countDownEvent` |  |
    | `setClock` |  | 
    | `changeCountDownTv` |  |
    | `getPetInfo` |  |
    | `getDiaryList` | 獲取`DiaryInfo`，若為空則插入一筆空資料 |
    | `getCurrentDate` | 獲得當前的日期 | 
    | `updatePetInfo` | 獲取`PetInfo`，若為空則插入一筆空資料 |
    | `updateDiaryInfo` |  |
   

---
 
## :clipboard: Release APK
APK version：[v1.20](https://drive.google.com/file/d/16f7JtEMNh8ebQh1cDulOO7-CLBmGnEXB/view?usp=sharing) | [v1.13](https://drive.google.com/file/d/1A8tu5XD8YOCGx4vqX-321naIeR-8RF8L/view?usp=sharing) | [v1.12](https://drive.google.com/file/d/1wyP16nUEpGKAprCsWNtp81e7WUQ_89tv/view?usp=sharing) | [v1.11](https://drive.google.com/file/d/1ZL_WONqEytPwCZU0J0CeNN4YGvFC5lGH/view?usp=sharing) | [v1.02](https://drive.google.com/file/d/1dKHy-yDFTvp73qgls_bgxlXCxnMNGoby/view?usp=sharing) | [v1.01](https://drive.google.com/file/d/1TUvKwZMFu0NKjmIGRJHOCriUb2u_sYeP/view?usp=sharing)

#### change log
* `v1.20` ( `2021.4.9` )：
    * 週報介面更改，修改運動建議
    * 寵物資訊更改，進化進度條為總分鐘數
    * 運動介面優化，放大圖示、加入動圖教學
* `v1.13` ( `2021.3.26` )
    * 修復異常
* `v1.12` ( `2021.3.26` )
    * 修復異常
* `v1.11` ( `2021.3.12` )
    * 按鈕與資訊的顏色區別
    * 運動介面任務完成加深顏色反饋
    * 週報介面減少文字敘述
* `v1.02` ( `2021.2.26` )
    * 新增主頁`bottom navigation`
    * 運動介面新增教學說明
    * 週報呈現長條圖
* `v1.01` ( `2021.2.1` )
    * 初步設計

---


