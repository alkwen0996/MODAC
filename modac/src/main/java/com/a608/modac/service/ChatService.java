package com.a608.modac.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.a608.modac.model.chat.ChatRoomDto;
import com.a608.modac.model.chat.DirectMessage;
import com.a608.modac.model.chat.DirectMessageDto;

public interface ChatService {
	void updateLastMessage(final DirectMessageDto directMessagedto);

	ChatRoomDto findChatRoomBySeq(final Long roomsSeq);

	List<ChatRoomDto> findAllChatRoomsByFollowingsSeq(final Long fromUsersSeq, final Long toUsersSeq);

	////////////////////////////redis//////////////////////////////////
	DirectMessage saveDirectMessage(final DirectMessageDto directMessageDto);

	List<DirectMessage> findAllMessagesByDirectChatRoomsSeq(final String chatRoomsSeq, final Pageable pageable);

	DirectMessage enterDirectChatRoom(final DirectMessageDto directMessageDto);

}

