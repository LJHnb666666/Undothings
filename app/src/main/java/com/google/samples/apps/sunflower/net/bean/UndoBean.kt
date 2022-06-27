package com.google.samples.apps.sunflower.net.bean

data class UndoBean(val id: Int,
                    val name: String,
                    val content: String,//详细内容
                    val createTime: String,//创建时间
                    val deadline: String,
                    val degree: Int,//重要程度 1>2>3
                    val today: Int,//是否为今日待办
                    val uid: Int)//所属用户id
