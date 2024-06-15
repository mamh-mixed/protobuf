// Protocol Buffers - Google's data interchange format
// Copyright 2024 Google LLC.  All rights reserved.
//
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file or at
// https://developers.google.com/open-source/licenses/bsd

use crate::{upb_MiniTable, RawMessage};

extern "C" {
    /// SAFETY: No constraints.
    pub fn upb_DebugString(
        msg: RawMessage,
        mt: *const upb_MiniTable,
        options: i32,
        buf: *mut u8,
        size: usize,
    ) -> usize;
}

#[allow(dead_code)]
#[allow(non_camel_case_types)]
enum Options {
    // When set, prints everything on a single line.
    UPB_TXTENC_SINGLELINE = 1,

    // When set, unknown fields are not printed.
    UPB_TXTENC_SKIPUNKNOWN = 2,

    // When set, maps are *not* sorted (this avoids allocating tmp mem).
    UPB_TXTENC_NOSORT = 4,
}

/// Returns a string of field number to value entries of a message. If we exceed
/// isize::MAX bytes of our string, it will return a truncated response of
/// length isize::MAX.
///
/// # Safety
/// - `mt` must correspond to the |msg|'s minitable.
pub unsafe fn debug_string(msg: RawMessage, mt: *const upb_MiniTable) -> String {
    // Only find out the length first to then allocate a buffer of the minimum size
    // needed.
    // SAFETY:
    // - `msg` is a legally dereferencable upb_Message whose associated minitable is
    //   `mt`
    // - `buf` is legally writable for 'buf_len' bytes
    let len = unsafe {
        upb_DebugString(msg, mt, Options::UPB_TXTENC_NOSORT as i32, std::ptr::null_mut(), 0)
    };
    assert!(len < isize::MAX as usize);
    let mut buf = vec![0u8; len];
    // SAFETY:
    // - `msg` is a legally dereferencable upb_Message whose associated minitable is
    //   `mt`
    // - `buf` is legally writable for 'buf_len' bytes
    let written_len = unsafe {
        upb_DebugString(msg, mt, Options::UPB_TXTENC_NOSORT as i32, buf.as_mut_ptr(), len)
    };
    assert_eq!(len, written_len);
    String::from_utf8_lossy(buf.as_slice()).to_string()
}
