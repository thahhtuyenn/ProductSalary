package com.product.salary.application.service.impl;

import com.product.salary.application.common.SystemConstants;
import com.product.salary.application.dao.ChucVuDAO;
import com.product.salary.application.dao.impl.ChucVuDAOImpl;
import com.product.salary.application.entity.ChucVu;
import com.product.salary.application.service.ChucVuService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChucVuServiceImpl implements ChucVuService {
	private final ChucVuDAO chucVuDao;

	public ChucVuServiceImpl() {
		this.chucVuDao = new ChucVuDAOImpl();
	}

	@Override
	public List<ChucVu> timKiemTatCaChucVu() {
		List<ChucVu> chucVus = new ArrayList<ChucVu>();
		try {

			chucVus = chucVuDao.timKiemTatCaChucVu();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hệ thống đang có lỗi.");
			e.printStackTrace();
		}
		return chucVus;
	}

	@Override
	public synchronized ChucVu capNhatChucVu(ChucVu chucVu) {
		try {
			ChucVu isExists = chucVuDao.timKiemBangMaChucVu(chucVu.getMaChucVu());
			if (isExists == null) {
				JOptionPane.showMessageDialog(null,
						SystemConstants.BUNDLE.getString("chucVu.thongBaoKhongTonTai"));
				return null;
			}
			isExists.setTenChucVu(chucVu.getTenChucVu());
			return chucVuDao.capNhatChucVu(isExists);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hệ thống đang có lỗi.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public synchronized ChucVu themChucVu(ChucVu chucVu) {
		try {
			chucVu.setMaChucVu(generateMaChucVu());
			ChucVu isExists = chucVuDao.timKiemBangMaChucVu(chucVu.getMaChucVu());
			if (isExists != null) {
				JOptionPane.showMessageDialog(null,
						SystemConstants.BUNDLE.getString("chucVu.thongBaoDaTonTai"));
				return null;
			}
			return chucVuDao.themChucVu(chucVu);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hệ thống đang có lỗi.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ChucVu timKiemBangMaChucVu(String maChucVu) {
		try {
			return chucVuDao.timKiemBangMaChucVu(maChucVu);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hệ thống đang có lỗi.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public synchronized String generateMaChucVu() {
		// 990001
		String rs = "990001";
		String maChuVu = chucVuDao.timMaChucVuCuoiCung();
		if (maChuVu != null) {
			String maCuoi = maChuVu.substring(2);
			int maMoi = Integer.parseInt(maCuoi);
			rs = String.format("99%04d", maMoi + 1);
		}
		return rs;
	}

	@Override
	public synchronized boolean xoaChucVuBangMa(String maChucVu) {
		try {
			return chucVuDao.xoaChucVuBangMa(maChucVu);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hệ thống đang có lỗi.");
			e.printStackTrace();
			return false;
		}
	}

}
