package com.appdevsolutions.chat.common.algo.sorter;
import java.util.Comparator;

import com.appdevsolutions.chat.common.model.AudioModel;
import com.appdevsolutions.chat.common.model.FileModel;
import com.appdevsolutions.chat.common.model.PhotoModel;
import com.appdevsolutions.chat.common.model.VideoModel;
import com.appdevsolutions.chat.web.model.EmailNotificationTemplateModel;
import com.appdevsolutions.chat.web.model.EmailTemplateModel;
import com.appdevsolutions.chat.web.model.MessageModel;
import com.appdevsolutions.chat.web.model.PrivilegeModel;
import com.appdevsolutions.chat.web.model.RoleModel;
import com.appdevsolutions.chat.web.model.UserModel;
public class TimeSorterAsc<T> implements Comparator<T>
{
	@Override
	public int compare(T obj1,T obj2){
		if(obj1 instanceof AudioModel && obj2 instanceof AudioModel){
			final AudioModel object1=(AudioModel)obj1;
			final AudioModel object2=(AudioModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof VideoModel &&obj2 instanceof VideoModel){
			final VideoModel object1=(VideoModel)obj1;
			final VideoModel object2=(VideoModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof PhotoModel && obj2 instanceof PhotoModel){
			final PhotoModel object1=(PhotoModel)obj1;
			final PhotoModel object2=(PhotoModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof FileModel &&obj2 instanceof FileModel){
			final FileModel object1=(FileModel)obj1;
			final FileModel object2=(FileModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof UserModel &&obj2 instanceof UserModel){
			final UserModel object1=(UserModel)obj1;
			final UserModel object2=(UserModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof RoleModel &&obj2 instanceof RoleModel){
			final RoleModel object1=(RoleModel)obj1;
			final RoleModel object2=(RoleModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof PrivilegeModel &&obj2 instanceof PrivilegeModel){
			final PrivilegeModel object1=(PrivilegeModel)obj1;
			final PrivilegeModel object2=(PrivilegeModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof MessageModel &&obj2 instanceof MessageModel){
			final MessageModel object1=(MessageModel)obj1;
			final MessageModel object2=(MessageModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}/*else if(obj1 instanceof FriendShipModel &&obj2 instanceof FriendShipModel){
			final FriendShipModel object1=(FriendShipModel)obj1;
			final FriendShipModel object2=(FriendShipModel)obj2;
			return object1.getId().compareTo(object2.get);
		}*/else if(obj1 instanceof EmailTemplateModel &&obj2 instanceof EmailTemplateModel){
			final EmailTemplateModel object1=(EmailTemplateModel)obj1;
			final EmailTemplateModel object2=(EmailTemplateModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else if(obj1 instanceof EmailNotificationTemplateModel &&obj2 instanceof EmailNotificationTemplateModel){
			final EmailNotificationTemplateModel object1=(EmailNotificationTemplateModel)obj1;
			final EmailNotificationTemplateModel object2=(EmailNotificationTemplateModel)obj2;
			return object1.getCreateTimestamp().compareTo(object2.getCreateTimestamp());
		}else{
			return 0;
		}
	}
}