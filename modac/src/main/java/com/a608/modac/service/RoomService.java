package com.a608.modac.service;

import java.util.List;

import com.a608.modac.model.participant.ParticipantRequest;
import com.a608.modac.model.room.RoomRequest;
import com.a608.modac.model.room.RoomResponse;

public interface RoomService {
	RoomResponse findRoomById(final Long seq);
	List<RoomResponse> findAllRooms(final Long usersSeq);
	List<RoomResponse> findMyRooms(final Long userSeq);
	RoomResponse createRoom(final RoomRequest roomRequest);
	RoomResponse updateRoom(final Long seq, final RoomRequest roomRequest);
	void deleteRoom(final Long seq);
	RoomResponse joinRoom(final Long seq, final Long userSeq);
	void exitRoom(Long seq, Long userSeq);
	void updateUserAttend(final Long seq, final ParticipantRequest participantRequest);
	boolean isMember(Long seq, Long userSeq);
	boolean isSameCode(Long seq, String code);
	List<RoomResponse> searchRooms(final Long usersSeq, final String keyword);
}
