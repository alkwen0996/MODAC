package com.a608.modac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a608.modac.model.participant.Participant;
import com.a608.modac.model.participant.ParticipantPK;
import com.a608.modac.model.room.Room;

public interface ParticipantRepository  extends JpaRepository<Participant, ParticipantPK> {

	List<Participant> findAllByParticipantPK_Room_Seq(Long roomSeq);

	List<Participant> findAllByParticipantPK_UsersSeq(Long userSeq);

	Participant findTopByParticipantPK_Room_SeqOrderByRegisteredTimeAsc(Long roomSeq);

	void deleteAllByParticipantPKRoom(Room room);
}
