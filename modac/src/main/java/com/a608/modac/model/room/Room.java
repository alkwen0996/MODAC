package com.a608.modac.model.room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.a608.modac.model.chat.ChatRoom;
import com.a608.modac.model.participant.Participant;
import com.a608.modac.model.participant.ParticipantResponse;
import com.a608.modac.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@ToString
@Table(name = "rooms")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")
	private Long seq;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name="max_size")
	private Integer maxSize;
	@Column(name = "current_size")
	private int currentSize;
	@Column(name="multi_theme")
	private String multiTheme;
	@Column(name="public_type")
	private Integer publicType;
	@Column(name="invitation_code")
	private String invitationCode;

	@JsonIgnore
	@OneToMany(mappedBy = "participantPK.room")
	private final List<Participant> participants = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name="users_seq")
	private User host;

	@OneToOne
	@JoinColumn(name = "chat_rooms_seq")
	private ChatRoom chatRoom;

	@Builder
	public Room(Long seq, String title, String description, Integer maxSize, int currentSize, String multiTheme, Integer publicType,
		String invitationCode,Participant participant, User host, ChatRoom chatRoom) {
		this.seq = seq;
		this.title = title;
		this.description = description;
		this.maxSize = maxSize;
		this.currentSize = currentSize;
		this.multiTheme = multiTheme;
		this.publicType = publicType;
		this.invitationCode = invitationCode;
		this.participants.add(participant);
		this.host = host;
		this.chatRoom = chatRoom;
	}

	@JsonProperty("participants")
	public List<ParticipantResponse> getParticipantDTOs() {
		System.out.println(participants);
		return participants.stream().map(Participant::getParticipant).collect(Collectors.toList());
	}


	public void updateRoom(final String title, final String description, final String multiTheme){
		this.title = title;
		this.description = description;
		this.multiTheme = multiTheme;
	}

	public void updateCurrentSize(final int cnt){
		this.currentSize += cnt;
	}

	public void participateRoom(Participant participant){
		updateCurrentSize(1);
		this.participants.add(participant);
	}

	public void exitRoom(Participant participant){
		updateCurrentSize(-1);
		this.participants.remove(participant);
	}

	public void updateHost(final User user) {
		this.host = user;
	}

}

