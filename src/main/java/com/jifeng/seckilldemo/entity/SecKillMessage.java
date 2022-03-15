package com.jifeng.seckilldemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecKillMessage {
    private Long goodsID;
    private User user;
}
