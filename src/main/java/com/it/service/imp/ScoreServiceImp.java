package com.it.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.mapper.ScoreMapper;
import com.it.model.score;
import com.it.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImp implements ScoreService{
    @Autowired
    private ScoreMapper scoreMapper;
    @Override
    public List<score> findScoresByTaskId(String taskId) {
        List<score> l=scoreMapper.findScoresByTaskId(taskId);
        return l;
    }

    @Override
    public score getScore(String studentNo) {
        return null;//！
    }

    @Override
    public List<score> findScoreByTaskId2(String taskId) {
        List<score> l=scoreMapper.findScoreByTaskId2(taskId);
        return l;
    }

    @Override
    public int updateScoreStatusById(score s) {
        int i=scoreMapper.updateScoreStatusById(s);
        return i;
    }

    @Override
    public List<score> findScoreByStudentNo(String studentNo) {
        List<score> l=scoreMapper.findScoreByStudentNo(studentNo);
        return l;
    }

    @Override
    public int updateScoreById(score s) {
        int i=scoreMapper.updateScoreById(s);
        return i;
    }

    @Override
    public score getScoreById(int id) {
        score s=scoreMapper.getScoreById(id);
        return s;
    }

    @Override
    public PageInfo<score> getScoreWithPage(String studentNo, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<score> l=scoreMapper.findScoreByStudentNo(studentNo);
        return new PageInfo<>(l);
    }

    @Override
    public int deleteScoreById(int id) {
        int i=scoreMapper.deleteScoreById(id);
        return i;
    }

    @Override
    public int deleteScoreByStudentNo(String studentNo) {
        int i=scoreMapper.deleteScoreByStudentNo(studentNo);
        return i;
    }
}
