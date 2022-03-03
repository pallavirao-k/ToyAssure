package com.increff.assure.pojo;
import com.increff.commons.Constants.Party;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import static com.increff.commons.Constants.ConstantNames.SEQ_PARTY;

@Entity
@Table(name="assureParty", indexes =@Index(name="member_index", columnList = "partyName, partyType", unique=true))
@Getter @Setter
public class PartyPojo extends AbstractPojo{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_PARTY)
	@TableGenerator(name=SEQ_PARTY, table=SEQ_PARTY)
	private Long partyId;

	@Column(nullable = false)
	private String partyName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Party.PartyType partyType;


}
