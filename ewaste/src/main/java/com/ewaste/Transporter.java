package com.ewaste;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "transporter")


public class Transporter {
	
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long tid;
		
		@OneToMany(fetch = FetchType.EAGER)
		@JoinColumn(name = "cart_id", nullable = false, referencedColumnName = "cid")
		private UserCart usercart;

		@OneToMany(fetch = FetchType.EAGER)
		@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "uid")
		private UserInfo userinfo;
		@Column(name = "transportation_status")
		private String transportation_status;
		public Long getTid() {
			return tid;
		}
		public void setTid(Long tid) {
			this.tid = tid;
		}
		public UserCart getUsercart() {
			return usercart;
		}
		public void setUsercart(UserCart usercart) {
			this.usercart = usercart;
		}
		public UserInfo getUserinfo() {
			return userinfo;
		}
		public void setUserinfo(UserInfo userinfo) {
			this.userinfo = userinfo;
		}
		public String getTransportation_status() {
			return transportation_status;
		}
		public void setTransportation_status(String transportation_status) {
			this.transportation_status = transportation_status;
		}


}
