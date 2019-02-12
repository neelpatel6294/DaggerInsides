 package com.techyourchance.journeytodependencyinjection.screens.questionslist;

 import android.os.Bundle;

 import com.techyourchance.journeytodependencyinjection.questions.FetchQuestionsListUseCase;
 import com.techyourchance.journeytodependencyinjection.questions.Question;
 import com.techyourchance.journeytodependencyinjection.screens.common.activities.BaseActivity;
 import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.DialogsManager;
 import com.techyourchance.journeytodependencyinjection.screens.common.dialogs.ServerErrorDialogFragment;
 import com.techyourchance.journeytodependencyinjection.screens.common.mvcviews.ViewMvcFactory;
 import com.techyourchance.journeytodependencyinjection.screens.questiondetails.QuestionDetailsActivity;

 import java.util.List;

 import javax.inject.Inject;

 public class QuestionsListActivity extends BaseActivity implements
         QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {

     private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;

     @Inject FetchQuestionsListUseCase mFetchQuestionsListUseCase;
     @Inject DialogsManager mDialogsManager;
     @Inject ViewMvcFactory mViewMvcFactory;

     private QuestionsListViewMvc mViewMvc;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         getPresentationComponent().inject(this);

         mViewMvc = mViewMvcFactory.newInstance(QuestionsListViewMvc.class, null);

         setContentView(mViewMvc.getRootView());

     }

     @Override
     protected void onStart() {
         super.onStart();
         mViewMvc.registerListener(this);
         mFetchQuestionsListUseCase.registerListener(this);

         mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
     }

     @Override
     protected void onStop() {
         super.onStop();
         mViewMvc.unregisterListener(this);
         mFetchQuestionsListUseCase.unregisterListener(this);
     }

     @Override
     public void onFetchOfQuestionsSucceeded(List<Question> questions) {
         mViewMvc.bindQuestions(questions);
     }

     @Override
     public void onFetchOfQuestionsFailed() {
         mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
     }

     @Override
     public void onQuestionClicked(Question question) {
         QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
     }
 }
