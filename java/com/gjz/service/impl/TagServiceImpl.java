package com.gjz.service.impl;

import com.gjz.dao.TagDao;
import com.gjz.exception.NotFoundException;
import com.gjz.pojo.Tag;
import com.gjz.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagDao.findById(id).orElse(null);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagDao.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                list.add(Long.valueOf(id));
            }
        }
        return tagDao.findAllById(list);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagDao.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);
        return tagDao.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagDao.deleteById(id);
    }
}
