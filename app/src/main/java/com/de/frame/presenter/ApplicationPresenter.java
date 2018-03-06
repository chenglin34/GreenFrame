package com.de.frame.presenter;

import com.hisign.csipadminifree.model.DataBackupRestoreModel;
import com.hisign.mpp.view.IApplicationView;

/**
 * 描述：
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：张绪飞
 * 创建时间:2017年07月04日
 * 最后修改时间:2017年07月04日
 */
public class ApplicationPresenter extends BaseNoModelPresenter<IApplicationView> {
    
    private DataBackupRestoreModel mBackupRestoreModel;
    
    public ApplicationPresenter() {
        mBackupRestoreModel = new DataBackupRestoreModel();
    }
    
    
    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        
    }
    
    /**
     * 备份数据
     */
    public void backupData() {
        mBackupRestoreModel.backup();
    }
    
    /**
     * 恢复数据
     */
    public void restoreData(String dbFileName) {
        mBackupRestoreModel.restore(dbFileName);
    }
    
    /**
     * 销毁（清除、线程、网络请求等）
     */
    @Override
    public void destroy() {
        
    }
}
