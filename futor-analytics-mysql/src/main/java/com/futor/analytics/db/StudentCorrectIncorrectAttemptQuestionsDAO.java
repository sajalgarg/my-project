package com.futor.analytics.db;

import java.util.Map;

public interface StudentCorrectIncorrectAttemptQuestionsDAO {
	Map<String,Object> correctIncorrectQuestionDetails(String courseId,long userId) throws Exception;
}
