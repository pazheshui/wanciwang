package com.it.service;

import com.github.pagehelper.PageInfo;
import com.it.model.score;

import java.util.List;

public interface ScoreService {
    List<score> findScoresByTaskId(String taskId);
    score getScore(String studentNo);
    List<score> findScoreByTaskId2(String taskId);
    int updateScoreStatusById(score s);
    List<score> findScoreByStudentNo(String studentNo);
    int updateScoreById(score s);
    score getScoreById(int id);

    PageInfo<score> getScoreWithPage(String studentNo,int page,int limit);

    int deleteScoreById(int id);

    int deleteScoreByStudentNo(String studentNo);
}
