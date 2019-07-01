package com.platform.admin.service.client.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TpwdParam implements Serializable {
   private long itemId;
   private String text;
   private String url;
}
