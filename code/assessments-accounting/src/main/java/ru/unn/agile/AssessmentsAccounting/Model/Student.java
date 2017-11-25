package ru.unn.agile.assessmentsaccounting.model;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private Map<String, List<Assessment>> assessments;

    public Student(final String name) {
        if (name.isEmpty()) {
            throw new InvalidParameterException();
        }
        this.name = name;
        this.assessments = new HashMap<String, List<Assessment>>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        if (name.isEmpty()) {
            throw new InvalidParameterException();
        }
        this.name = name;
    }

    public void addSubject(final String subject) {
        if (subject.isEmpty()) {
            throw new InvalidParameterException();
        }
        assessments.put(subject, new ArrayList<Assessment>());
    }

    public void renameSubject(final String oldName, final String newName) {
        if (!assessments.containsKey(oldName) || newName.isEmpty()) {
            throw new InvalidParameterException();
        }
        assessments.put(newName, assessments.get(oldName));
        assessments.remove(oldName);
    }

    public void removeSubject(final String subject) {
        if (!assessments.containsKey(subject)) {
            throw new InvalidParameterException();
        }
        assessments.remove(subject);
    }

    public void addAssessment(final Assessment assessment, final String subject) {
        List<Assessment> subjectAssessments =
                assessments.getOrDefault(subject, new ArrayList<Assessment>());
        subjectAssessments.add(assessment);
        assessments.put(subject, subjectAssessments);
    }

    public void removeAssessmentAt(final int index, final String subject) {
        checkAssessmentsIndex(index, subject);
        List<Assessment> subjectAssessments = assessments.get(subject);
        subjectAssessments.remove(index);
        assessments.put(subject, subjectAssessments);
    }

    public void changeAssessmentAt(final int index,
                                   final Assessment assessment,
                                   final String subject) {
        checkAssessmentsIndex(index, subject);
        List<Assessment> subjectAssessments = assessments.get(subject);
        subjectAssessments.set(index, assessment);
        assessments.put(subject, subjectAssessments);
    }

    public List<Assessment> getAssessments() {
        List<Assessment> allAssessments = new ArrayList<Assessment>();
        for (Map.Entry<String, List<Assessment>> entry : assessments.entrySet()) {
            allAssessments.addAll(entry.getValue());
        }
        return allAssessments;
    }

    public List<Assessment> getAssessments(final String subject) {
        return assessments.getOrDefault(subject, null);
    }

    private void checkAssessmentsIndex(final int index, final String subject) {
        if (!assessments.containsKey(subject)) {
            throw new InvalidParameterException();
        }
        if (index < 0 || index >= assessments.get(subject).size()) {
            throw new InvalidParameterException();
        }
    }
}
