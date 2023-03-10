package com.a608.modac.model.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import org.hibernate.annotations.DynamicInsert;

import com.a608.modac.model.category.Category;
import com.a608.modac.model.user.User;

@Getter
@Entity
@ToString
@DynamicInsert
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name="users_seq")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name="status")
    private Byte status;

    @Column(name = "total_second")
    private String totalSecond;

    @ManyToOne
    @JoinColumn(name = "categories_name")
    private Category category;

    @Builder
    public Todo(final Long seq, final User user, final String title, final Byte status, final String totalSecond, final Category category) {
        this.seq = seq;
        this.user = user;
        this.title = title;
        this.status = status;
        this.totalSecond = totalSecond;
        this.category = category;
    }

    public Todo() {
    }

    public void updateTodo(final String title, final Byte status, final String totalSecond, final Category category){
        this.title = title;
        this.status = status;
        this.totalSecond = totalSecond;
        this.category = category;
    }

}
