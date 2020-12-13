package com.video.tanmu.service.impl;

import com.video.tanmu.dao.TypeDao;
import com.video.tanmu.dao.VideoDao;
import com.video.tanmu.model.TypeModel;
import com.video.tanmu.model.VideoModel;
import com.video.tanmu.result.Response;
import com.video.tanmu.service.IndexService;
import com.video.tanmu.utils.ConvertUtils;
import com.video.tanmu.vo.IndexTreeVo;
import com.video.tanmu.vo.TypeVo;
import com.video.tanmu.vo.TypeWithVideoListVo;
import com.video.tanmu.vo.VideoListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private TypeDao typeDao;

    @Autowired
    private VideoDao videoDao;

    @Override
    public Response<IndexTreeVo> getIndexTree() {
        IndexTreeVo indexTreeVo = new IndexTreeVo();
        List<TypeModel> allTypeModel = typeDao.selectAll();
        List<TypeVo> parentTypeList = new ArrayList<>();
        List<TypeWithVideoListVo> typeWithVideoListVoList = new ArrayList<>();

        for (TypeModel typeModel : allTypeModel) {
            if (!typeModel.getParentType().equals(0)) {
                 continue;
            }
            parentTypeList.add(ConvertUtils.copyProperties(typeModel, TypeVo.class));
            List<VideoModel> videoModels = videoDao.selectByTypeId(typeModel.getId(), 18);
            TypeWithVideoListVo typeWithVideoListVo = new TypeWithVideoListVo();
            typeWithVideoListVo.setTypeId(typeModel.getId());
            typeWithVideoListVo.setTypeName(typeModel.getName());
            List<VideoListVo> videoListVos = videoModels.stream().map(VideoListVo::convertFromVideoModel).collect(Collectors.toList());
            typeWithVideoListVo.setVideoListVos(videoListVos);
            typeWithVideoListVoList.add(typeWithVideoListVo);
        }
        indexTreeVo.setTypeVoList(parentTypeList);
        indexTreeVo.setTypeWithVideoListVoList(typeWithVideoListVoList);
        return Response.success(indexTreeVo);
    }
}